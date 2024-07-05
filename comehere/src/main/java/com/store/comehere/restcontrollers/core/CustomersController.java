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
import com.store.comehere.application.core.customers.ICustomersAppService;
import com.store.comehere.application.core.customers.dto.*;
import com.store.comehere.application.core.orders.IOrdersAppService;
import com.store.comehere.application.core.orders.dto.FindOrdersByIdOutput;
import java.util.*;
import java.time.*;
import java.net.MalformedURLException;
import com.store.comehere.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomersController {

	@Qualifier("customersAppService")
	@NonNull protected final ICustomersAppService _customersAppService;
    @Qualifier("ordersAppService")
	@NonNull  protected final IOrdersAppService  _ordersAppService;

	@NonNull protected final LoggingHelper logHelper;

	@NonNull protected final Environment env;

    @PreAuthorize("hasAnyAuthority('CUSTOMERSENTITY_CREATE')")
	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<CreateCustomersOutput> create( @RequestBody @Valid CreateCustomersInput customers) {
		CreateCustomersOutput output=_customersAppService.create(customers);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}

	// ------------ Delete customers ------------
	@PreAuthorize("hasAnyAuthority('CUSTOMERSENTITY_DELETE')")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = {"application/json"})
	public void delete(@PathVariable String id) {

    	FindCustomersByIdOutput output = _customersAppService.findById(Integer.valueOf(id));
    	if(output == null) {
    		throw new EntityNotFoundException(String.format("There does not exist a customers with a id=%s", id));
    	}	

    	_customersAppService.delete(Integer.valueOf(id));
    }


	// ------------ Update customers ------------
    @PreAuthorize("hasAnyAuthority('CUSTOMERSENTITY_UPDATE')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<UpdateCustomersOutput> update(@PathVariable String id,  @RequestBody @Valid UpdateCustomersInput customers) {

	    FindCustomersByIdOutput currentCustomers = _customersAppService.findById(Integer.valueOf(id));
		if(currentCustomers == null) {
			throw new EntityNotFoundException(String.format("Unable to update. Customers with id=%s not found.", id));
		}

		customers.setVersiono(currentCustomers.getVersiono());
	    UpdateCustomersOutput output = _customersAppService.update(Integer.valueOf(id),customers);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    

    @PreAuthorize("hasAnyAuthority('CUSTOMERSENTITY_READ')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<FindCustomersByIdOutput> findById(@PathVariable String id) {

    	FindCustomersByIdOutput output = _customersAppService.findById(Integer.valueOf(id));
        if(output == null) {
    		throw new EntityNotFoundException("Not found");
    	}
    	
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    @PreAuthorize("hasAnyAuthority('CUSTOMERSENTITY_READ')")
	@RequestMapping(method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<List<FindCustomersByIdOutput>> find(@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws EntityNotFoundException, MalformedURLException {

		if (offset == null) { offset = env.getProperty("comehere.offset.default"); }
		if (limit == null) { limit = env.getProperty("comehere.limit.default"); }

		Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

		return new ResponseEntity<>(_customersAppService.find(searchCriteria,Pageable), HttpStatus.OK);
	}
	
    @PreAuthorize("hasAnyAuthority('CUSTOMERSENTITY_READ')")
	@RequestMapping(value = "/{id}/orders", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<List<FindOrdersByIdOutput>> getOrders(@PathVariable String id, @RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws EntityNotFoundException, MalformedURLException {
   		if (offset == null) { offset = env.getProperty("comehere.offset.default"); }
		if (limit == null) { limit = env.getProperty("comehere.limit.default"); }

		Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);

		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);
		Map<String,String> joinColDetails=_customersAppService.parseOrdersJoinColumn(id);
		if(joinColDetails == null) {
			throw new EntityNotFoundException("Invalid join column");
		}

		searchCriteria.setJoinColumns(joinColDetails);

    	List<FindOrdersByIdOutput> output = _ordersAppService.find(searchCriteria,pageable);
    	
    	if(output == null) {
			throw new EntityNotFoundException("Not found");
		}
		
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
}


