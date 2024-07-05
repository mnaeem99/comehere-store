package com.store.comehere.application.core.orderitems;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import com.store.comehere.application.core.orderitems.dto.*;
import com.store.comehere.domain.core.orderitems.IOrderItemsRepository;
import com.store.comehere.domain.core.orderitems.QOrderItems;
import com.store.comehere.domain.core.orderitems.OrderItems;
import com.store.comehere.domain.core.orders.IOrdersRepository;
import com.store.comehere.domain.core.orders.Orders;
import com.store.comehere.domain.core.products.IProductsRepository;
import com.store.comehere.domain.core.products.Products;


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

@Service("orderItemsAppService")
@RequiredArgsConstructor
public class OrderItemsAppService implements IOrderItemsAppService {
    
	@Qualifier("orderItemsRepository")
	@NonNull protected final IOrderItemsRepository _orderItemsRepository;

	
    @Qualifier("ordersRepository")
	@NonNull protected final IOrdersRepository _ordersRepository;

    @Qualifier("productsRepository")
	@NonNull protected final IProductsRepository _productsRepository;

	@Qualifier("IOrderItemsMapperImpl")
	@NonNull protected final IOrderItemsMapper mapper;

	@NonNull protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
	public CreateOrderItemsOutput create(CreateOrderItemsInput input) {

		OrderItems orderItems = mapper.createOrderItemsInputToOrderItems(input);
		Orders foundOrders = null;
		Products foundProducts = null;
	  	if(input.getOrderId()!=null) {
			foundOrders = _ordersRepository.findById(input.getOrderId()).orElse(null);
			
			if(foundOrders!=null) {
				foundOrders.addOrderItems(orderItems);
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
	  	if(input.getProductId()!=null) {
			foundProducts = _productsRepository.findById(input.getProductId()).orElse(null);
			
			if(foundProducts!=null) {
				foundProducts.addOrderItems(orderItems);
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}

		OrderItems createdOrderItems = _orderItemsRepository.save(orderItems);
		return mapper.orderItemsToCreateOrderItemsOutput(createdOrderItems);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public UpdateOrderItemsOutput update(Integer orderItemsId, UpdateOrderItemsInput input) {

		OrderItems existing = _orderItemsRepository.findById(orderItemsId).orElseThrow(() -> new EntityNotFoundException("OrderItems not found"));

		OrderItems orderItems = mapper.updateOrderItemsInputToOrderItems(input);
		Orders foundOrders = null;
		Products foundProducts = null;
        
	  	if(input.getOrderId()!=null) { 
			foundOrders = _ordersRepository.findById(input.getOrderId()).orElse(null);
		
			if(foundOrders!=null) {
				foundOrders.addOrderItems(orderItems);
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
        
	  	if(input.getProductId()!=null) { 
			foundProducts = _productsRepository.findById(input.getProductId()).orElse(null);
		
			if(foundProducts!=null) {
				foundProducts.addOrderItems(orderItems);
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
		
		OrderItems updatedOrderItems = _orderItemsRepository.save(orderItems);
		return mapper.orderItemsToUpdateOrderItemsOutput(updatedOrderItems);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Integer orderItemsId) {

		OrderItems existing = _orderItemsRepository.findById(orderItemsId).orElseThrow(() -> new EntityNotFoundException("OrderItems not found"));
	 	
        if(existing.getOrders() !=null)
        {
        existing.getOrders().removeOrderItems(existing);
        }
        if(existing.getProducts() !=null)
        {
        existing.getProducts().removeOrderItems(existing);
        }
        if(existing !=null) {
			_orderItemsRepository.delete(existing);
		}
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public FindOrderItemsByIdOutput findById(Integer orderItemsId) {

		OrderItems foundOrderItems = _orderItemsRepository.findById(orderItemsId).orElse(null);
		if (foundOrderItems == null)  
			return null; 
 	   
 	    return mapper.orderItemsToFindOrderItemsByIdOutput(foundOrderItems);
	}

    //Orders
	// ReST API Call - GET /orderItems/1/orders
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public GetOrdersOutput getOrders(Integer orderItemsId) {

		OrderItems foundOrderItems = _orderItemsRepository.findById(orderItemsId).orElse(null);
		if (foundOrderItems == null) {
			logHelper.getLogger().error("There does not exist a orderItems wth a id='{}'", orderItemsId);
			return null;
		}
		Orders re = foundOrderItems.getOrders();
		return mapper.ordersToGetOrdersOutput(re, foundOrderItems);
	}
	
    //Products
	// ReST API Call - GET /orderItems/1/products
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public GetProductsOutput getProducts(Integer orderItemsId) {

		OrderItems foundOrderItems = _orderItemsRepository.findById(orderItemsId).orElse(null);
		if (foundOrderItems == null) {
			logHelper.getLogger().error("There does not exist a orderItems wth a id='{}'", orderItemsId);
			return null;
		}
		Products re = foundOrderItems.getProducts();
		return mapper.productsToGetProductsOutput(re, foundOrderItems);
	}
	
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<FindOrderItemsByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException  {

		Page<OrderItems> foundOrderItems = _orderItemsRepository.findAll(search(search), pageable);
		List<OrderItems> orderItemsList = foundOrderItems.getContent();
		Iterator<OrderItems> orderItemsIterator = orderItemsList.iterator(); 
		List<FindOrderItemsByIdOutput> output = new ArrayList<>();

		while (orderItemsIterator.hasNext()) {
		OrderItems orderItems = orderItemsIterator.next();
 	    output.add(mapper.orderItemsToFindOrderItemsByIdOutput(orderItems));
		}
		return output;
	}
	
	protected BooleanBuilder search(SearchCriteria search) throws MalformedURLException {

		QOrderItems orderItems= QOrderItems.orderItemsEntity;
		if(search != null) {
			Map<String,SearchFields> map = new HashMap<>();
			for(SearchFields fieldDetails: search.getFields())
			{
				map.put(fieldDetails.getFieldName(),fieldDetails);
			}
			List<String> keysList = new ArrayList<String>(map.keySet());
			checkProperties(keysList);
			return searchKeyValuePair(orderItems, map,search.getJoinColumns());
		}
		return null;
	}
	
	protected void checkProperties(List<String> list) throws MalformedURLException  {
		for (int i = 0; i < list.size(); i++) {
			if(!(
		        list.get(i).replace("%20","").trim().equals("orders") ||
				list.get(i).replace("%20","").trim().equals("orderId") ||
		        list.get(i).replace("%20","").trim().equals("products") ||
				list.get(i).replace("%20","").trim().equals("productId") ||
				list.get(i).replace("%20","").trim().equals("orderItemId") ||
				list.get(i).replace("%20","").trim().equals("price") ||
				list.get(i).replace("%20","").trim().equals("quantity")
			)) 
			{
			 throw new MalformedURLException("Wrong URL Format: Property " + list.get(i) + " not found!" );
			}
		}
	}
	
	protected BooleanBuilder searchKeyValuePair(QOrderItems orderItems, Map<String,SearchFields> map,Map<String,String> joinColumns) {
		BooleanBuilder builder = new BooleanBuilder();
        
		Iterator<Map.Entry<String, SearchFields>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, SearchFields> details = iterator.next();

			if(details.getKey().replace("%20","").trim().equals("orderItemId")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orderItems.orderItemId.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orderItems.orderItemId.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orderItems.orderItemId.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(orderItems.orderItemId.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(orderItems.orderItemId.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(orderItems.orderItemId.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
			if(details.getKey().replace("%20","").trim().equals("price")) {
				if(details.getValue().getOperator().equals("contains") && NumberUtils.isCreatable(details.getValue().getSearchValue())) {
					builder.and(orderItems.price.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && NumberUtils.isCreatable(details.getValue().getSearchValue())) {
					builder.and(orderItems.price.eq(new BigDecimal(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && NumberUtils.isCreatable(details.getValue().getSearchValue())) {
					builder.and(orderItems.price.ne(new BigDecimal(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(NumberUtils.isCreatable(details.getValue().getStartingValue()) && NumberUtils.isCreatable(details.getValue().getEndingValue())) {
                	   builder.and(orderItems.price.between(new BigDecimal(details.getValue().getStartingValue()), new BigDecimal(details.getValue().getEndingValue())));
                   } else if(NumberUtils.isCreatable(details.getValue().getStartingValue())) {
                	   builder.and(orderItems.price.goe(new BigDecimal(details.getValue().getStartingValue())));
                   } else if(NumberUtils.isCreatable(details.getValue().getEndingValue())) {
                	   builder.and(orderItems.price.loe(new BigDecimal(details.getValue().getEndingValue())));
				   }
				}
			}		
			if(details.getKey().replace("%20","").trim().equals("quantity")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orderItems.quantity.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orderItems.quantity.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orderItems.quantity.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(orderItems.quantity.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(orderItems.quantity.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(orderItems.quantity.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
	    
		     if(details.getKey().replace("%20","").trim().equals("orders")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orderItems.orders.orderId.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orderItems.orders.orderId.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orderItems.orders.orderId.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(orderItems.orders.orderId.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(orderItems.orders.orderId.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(orderItems.orders.orderId.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
		     if(details.getKey().replace("%20","").trim().equals("products")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orderItems.products.productId.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orderItems.products.productId.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orderItems.products.productId.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(orderItems.products.productId.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(orderItems.products.productId.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(orderItems.products.productId.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
		}
		
		for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
		if(joinCol != null && joinCol.getKey().equals("orderId")) {
		    builder.and(orderItems.orders.orderId.eq(Integer.parseInt(joinCol.getValue())));
		}
        
        }
		for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
		if(joinCol != null && joinCol.getKey().equals("productId")) {
		    builder.and(orderItems.products.productId.eq(Integer.parseInt(joinCol.getValue())));
		}
        
        }
		return builder;
	}
	
    
    
}



