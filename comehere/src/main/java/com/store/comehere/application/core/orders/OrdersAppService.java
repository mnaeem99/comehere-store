package com.store.comehere.application.core.orders;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import com.store.comehere.application.core.orders.dto.*;
import com.store.comehere.domain.core.orders.IOrdersRepository;
import com.store.comehere.domain.core.orders.QOrders;
import com.store.comehere.domain.core.orders.Orders;
import com.store.comehere.domain.core.customers.ICustomersRepository;
import com.store.comehere.domain.core.customers.Customers;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page; 
import org.springframework.data.domain.Pageable; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import com.store.comehere.commons.search.*;
import com.store.comehere.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import java.net.MalformedURLException;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import javax.persistence.EntityNotFoundException;

@Service("ordersAppService")
@RequiredArgsConstructor
public class OrdersAppService implements IOrdersAppService {
    
	@Qualifier("ordersRepository")
	@NonNull protected final IOrdersRepository _ordersRepository;

	
    @Qualifier("customersRepository")
	@NonNull protected final ICustomersRepository _customersRepository;

	@Qualifier("IOrdersMapperImpl")
	@NonNull protected final IOrdersMapper mapper;

	@NonNull protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
	public CreateOrdersOutput create(CreateOrdersInput input) {

		Orders orders = mapper.createOrdersInputToOrders(input);
		Customers foundCustomers = null;
	  	if(input.getCustomerId()!=null) {
			foundCustomers = _customersRepository.findById(input.getCustomerId()).orElse(null);
			
			if(foundCustomers!=null) {
				foundCustomers.addOrders(orders);
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}

		Orders createdOrders = _ordersRepository.save(orders);
		return mapper.ordersToCreateOrdersOutput(createdOrders);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public UpdateOrdersOutput update(Integer ordersId, UpdateOrdersInput input) {

		Orders existing = _ordersRepository.findById(ordersId).orElseThrow(() -> new EntityNotFoundException("Orders not found"));

		Orders orders = mapper.updateOrdersInputToOrders(input);
		orders.setOrderItemsSet(existing.getOrderItemsSet());
		Customers foundCustomers = null;
        
	  	if(input.getCustomerId()!=null) { 
			foundCustomers = _customersRepository.findById(input.getCustomerId()).orElse(null);
		
			if(foundCustomers!=null) {
				foundCustomers.addOrders(orders);
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
		
		Orders updatedOrders = _ordersRepository.save(orders);
		return mapper.ordersToUpdateOrdersOutput(updatedOrders);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Integer ordersId) {

		Orders existing = _ordersRepository.findById(ordersId).orElseThrow(() -> new EntityNotFoundException("Orders not found"));
	 	
        if(existing.getCustomers() !=null)
        {
        existing.getCustomers().removeOrders(existing);
        }
        if(existing !=null) {
			_ordersRepository.delete(existing);
		}
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public FindOrdersByIdOutput findById(Integer ordersId) {

		Orders foundOrders = _ordersRepository.findById(ordersId).orElse(null);
		if (foundOrders == null)  
			return null; 
 	   
 	    return mapper.ordersToFindOrdersByIdOutput(foundOrders);
	}

    //Customers
	// ReST API Call - GET /orders/1/customers
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public GetCustomersOutput getCustomers(Integer ordersId) {

		Orders foundOrders = _ordersRepository.findById(ordersId).orElse(null);
		if (foundOrders == null) {
			logHelper.getLogger().error("There does not exist a orders wth a id='{}'", ordersId);
			return null;
		}
		Customers re = foundOrders.getCustomers();
		return mapper.customersToGetCustomersOutput(re, foundOrders);
	}
	
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<FindOrdersByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException  {

		Page<Orders> foundOrders = _ordersRepository.findAll(search(search), pageable);
		List<Orders> ordersList = foundOrders.getContent();
		Iterator<Orders> ordersIterator = ordersList.iterator(); 
		List<FindOrdersByIdOutput> output = new ArrayList<>();

		while (ordersIterator.hasNext()) {
		Orders orders = ordersIterator.next();
 	    output.add(mapper.ordersToFindOrdersByIdOutput(orders));
		}
		return output;
	}
	
	protected BooleanBuilder search(SearchCriteria search) throws MalformedURLException {

		QOrders orders= QOrders.ordersEntity;
		if(search != null) {
			Map<String,SearchFields> map = new HashMap<>();
			for(SearchFields fieldDetails: search.getFields())
			{
				map.put(fieldDetails.getFieldName(),fieldDetails);
			}
			List<String> keysList = new ArrayList<String>(map.keySet());
			checkProperties(keysList);
			return searchKeyValuePair(orders, map,search.getJoinColumns());
		}
		return null;
	}
	
	protected void checkProperties(List<String> list) throws MalformedURLException  {
		for (int i = 0; i < list.size(); i++) {
			if(!(
		        list.get(i).replace("%20","").trim().equals("customers") ||
				list.get(i).replace("%20","").trim().equals("customerId") ||
				list.get(i).replace("%20","").trim().equals("orderDate") ||
				list.get(i).replace("%20","").trim().equals("orderId") ||
				list.get(i).replace("%20","").trim().equals("status") ||
				list.get(i).replace("%20","").trim().equals("total")
			)) 
			{
			 throw new MalformedURLException("Wrong URL Format: Property " + list.get(i) + " not found!" );
			}
		}
	}
	
	protected BooleanBuilder searchKeyValuePair(QOrders orders, Map<String,SearchFields> map,Map<String,String> joinColumns) {
		BooleanBuilder builder = new BooleanBuilder();
        
		Iterator<Map.Entry<String, SearchFields>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, SearchFields> details = iterator.next();

			if(details.getKey().replace("%20","").trim().equals("orderDate")) {
				if(details.getValue().getOperator().equals("equals") && SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) !=null) {
					builder.and(orders.orderDate.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) !=null) {
					builder.and(orders.orderDate.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   LocalDateTime startLocalDateTime= SearchUtils.stringToLocalDateTime(details.getValue().getStartingValue());
				   LocalDateTime endLocalDateTime= SearchUtils.stringToLocalDateTime(details.getValue().getEndingValue());
				   if(startLocalDateTime!=null && endLocalDateTime!=null) {
					   builder.and(orders.orderDate.between(startLocalDateTime,endLocalDateTime));
				   } else if(endLocalDateTime!=null) {
					   builder.and(orders.orderDate.loe(endLocalDateTime));
                   } else if(startLocalDateTime!=null) {
                	   builder.and(orders.orderDate.goe(startLocalDateTime));  
                   }
                }     
			}
			if(details.getKey().replace("%20","").trim().equals("orderId")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orders.orderId.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orders.orderId.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orders.orderId.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(orders.orderId.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(orders.orderId.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(orders.orderId.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
            if(details.getKey().replace("%20","").trim().equals("status")) {
				if(details.getValue().getOperator().equals("contains")) {
					builder.and(orders.status.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals")) {
					builder.and(orders.status.eq(details.getValue().getSearchValue()));
				} else if(details.getValue().getOperator().equals("notEqual")) {
					builder.and(orders.status.ne(details.getValue().getSearchValue()));
				}
			}
			if(details.getKey().replace("%20","").trim().equals("total")) {
				if(details.getValue().getOperator().equals("contains") && NumberUtils.isCreatable(details.getValue().getSearchValue())) {
					builder.and(orders.total.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && NumberUtils.isCreatable(details.getValue().getSearchValue())) {
					builder.and(orders.total.eq(new BigDecimal(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && NumberUtils.isCreatable(details.getValue().getSearchValue())) {
					builder.and(orders.total.ne(new BigDecimal(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(NumberUtils.isCreatable(details.getValue().getStartingValue()) && NumberUtils.isCreatable(details.getValue().getEndingValue())) {
                	   builder.and(orders.total.between(new BigDecimal(details.getValue().getStartingValue()), new BigDecimal(details.getValue().getEndingValue())));
                   } else if(NumberUtils.isCreatable(details.getValue().getStartingValue())) {
                	   builder.and(orders.total.goe(new BigDecimal(details.getValue().getStartingValue())));
                   } else if(NumberUtils.isCreatable(details.getValue().getEndingValue())) {
                	   builder.and(orders.total.loe(new BigDecimal(details.getValue().getEndingValue())));
				   }
				}
			}		
	    
		    if(details.getKey().replace("%20","").trim().equals("customers")) {
				if(details.getValue().getOperator().equals("contains")) {
					builder.and(orders.customers.email.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals")) {
					builder.and(orders.customers.email.eq(details.getValue().getSearchValue()));
				} else if(details.getValue().getOperator().equals("notEqual")) {
					builder.and(orders.customers.email.ne(details.getValue().getSearchValue()));
				}
			}
		}
		
		for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
		if(joinCol != null && joinCol.getKey().equals("customerId")) {
		    builder.and(orders.customers.customerId.eq(Integer.parseInt(joinCol.getValue())));
		}
        
		if(joinCol != null && joinCol.getKey().equals("customers")) {
		    builder.and(orders.customers.email.eq(joinCol.getValue()));
        }
        }
		return builder;
	}
	
    
	public Map<String,String> parseOrderItemsJoinColumn(String keysString) {
		
		Map<String,String> joinColumnMap = new HashMap<String,String>();
		joinColumnMap.put("orderId", keysString);
		  
		return joinColumnMap;
	}
    
}



