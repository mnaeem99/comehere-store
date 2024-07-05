package com.store.comehere.restcontrollers.core;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.store.comehere.commons.search.SearchCriteria;
import com.store.comehere.commons.search.SearchUtils;
import com.store.comehere.commons.search.OffsetBasedPageRequest;
import com.store.comehere.application.core.orderitems.IOrderItemsAppService;
import com.store.comehere.application.core.orderitems.dto.*;
import com.store.comehere.application.core.orders.IOrdersAppService;
import com.store.comehere.application.core.products.IProductsAppService;
import java.util.*;
import java.time.*;
import java.net.MalformedURLException;
import com.store.comehere.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/orderItems")
@RequiredArgsConstructor
public class OrderItemsController {

	@Qualifier("orderItemsAppService")
	@NonNull protected final IOrderItemsAppService _orderItemsAppService;
    @Qualifier("ordersAppService")
	@NonNull  protected final IOrdersAppService  _ordersAppService;

    @Qualifier("productsAppService")
	@NonNull  protected final IProductsAppService  _productsAppService;

	@NonNull protected final LoggingHelper logHelper;

	@NonNull protected final Environment env;

    @PreAuthorize("hasAnyAuthority('ORDERITEMSENTITY_CREATE')")
	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<CreateOrderItemsOutput> create( @RequestBody @Valid CreateOrderItemsInput orderItems) {
		CreateOrderItemsOutput output=_orderItemsAppService.create(orderItems);
		if(output == null) {
			throw new EntityNotFoundException("No record found");
		}
		return new ResponseEntity<>(output, HttpStatus.OK);
	}

	// ------------ Delete orderItems ------------
	@PreAuthorize("hasAnyAuthority('ORDERITEMSENTITY_DELETE')")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = {"application/json"})
	public void delete(@PathVariable String id) {

    	FindOrderItemsByIdOutput output = _orderItemsAppService.findById(Integer.valueOf(id));
    	if(output == null) {
    		throw new EntityNotFoundException(String.format("There does not exist a orderItems with a id=%s", id));
    	}	

    	_orderItemsAppService.delete(Integer.valueOf(id));
    }


	// ------------ Update orderItems ------------
    @PreAuthorize("hasAnyAuthority('ORDERITEMSENTITY_UPDATE')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<UpdateOrderItemsOutput> update(@PathVariable String id,  @RequestBody @Valid UpdateOrderItemsInput orderItems) {

	    FindOrderItemsByIdOutput currentOrderItems = _orderItemsAppService.findById(Integer.valueOf(id));
		if(currentOrderItems == null) {
			throw new EntityNotFoundException(String.format("Unable to update. OrderItems with id=%s not found.", id));
		}

		orderItems.setVersiono(currentOrderItems.getVersiono());
	    UpdateOrderItemsOutput output = _orderItemsAppService.update(Integer.valueOf(id),orderItems);
		if(output == null) {
    		throw new EntityNotFoundException("No record found");
    	}
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    

    @PreAuthorize("hasAnyAuthority('ORDERITEMSENTITY_READ')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<FindOrderItemsByIdOutput> findById(@PathVariable String id) {

    	FindOrderItemsByIdOutput output = _orderItemsAppService.findById(Integer.valueOf(id));
        if(output == null) {
    		throw new EntityNotFoundException("Not found");
    	}
    	
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    @PreAuthorize("hasAnyAuthority('ORDERITEMSENTITY_READ')")
	@RequestMapping(method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<List<FindOrderItemsByIdOutput>> find(@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws EntityNotFoundException, MalformedURLException {

		if (offset == null) { offset = env.getProperty("comehere.offset.default"); }
		if (limit == null) { limit = env.getProperty("comehere.limit.default"); }

		Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

		return new ResponseEntity<>(_orderItemsAppService.find(searchCriteria,Pageable), HttpStatus.OK);
	}
	
    @PreAuthorize("hasAnyAuthority('ORDERITEMSENTITY_READ')")
	@RequestMapping(value = "/{id}/orders", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<GetOrdersOutput> getOrders(@PathVariable String id) {
    	GetOrdersOutput output= _orderItemsAppService.getOrders(Integer.valueOf(id));
		if(output ==null) {
			throw new EntityNotFoundException("Not found");
	    }		

		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    @PreAuthorize("hasAnyAuthority('ORDERITEMSENTITY_READ')")
	@RequestMapping(value = "/{id}/products", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<GetProductsOutput> getProducts(@PathVariable String id) {
    	GetProductsOutput output= _orderItemsAppService.getProducts(Integer.valueOf(id));
		if(output ==null) {
			throw new EntityNotFoundException("Not found");
	    }		

		return new ResponseEntity<>(output, HttpStatus.OK);
	}
}


