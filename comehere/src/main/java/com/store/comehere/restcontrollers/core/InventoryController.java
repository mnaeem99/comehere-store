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
import com.store.comehere.application.core.inventory.IInventoryAppService;
import com.store.comehere.application.core.inventory.dto.*;
import com.store.comehere.application.core.products.IProductsAppService;
import com.store.comehere.application.core.suppliers.ISuppliersAppService;
import java.util.*;
import java.time.*;
import java.net.MalformedURLException;
import com.store.comehere.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

	@Qualifier("inventoryAppService")
	@NonNull protected final IInventoryAppService _inventoryAppService;
    @Qualifier("productsAppService")
	@NonNull  protected final IProductsAppService  _productsAppService;

    @Qualifier("suppliersAppService")
	@NonNull  protected final ISuppliersAppService  _suppliersAppService;

	@NonNull protected final LoggingHelper logHelper;

	@NonNull protected final Environment env;

    @PreAuthorize("hasAnyAuthority('INVENTORYENTITY_CREATE')")
	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<CreateInventoryOutput> create( @RequestBody @Valid CreateInventoryInput inventory) {
		CreateInventoryOutput output=_inventoryAppService.create(inventory);
		if(output == null) {
			throw new EntityNotFoundException("No record found");
		}
		return new ResponseEntity<>(output, HttpStatus.OK);
	}

	// ------------ Delete inventory ------------
	@PreAuthorize("hasAnyAuthority('INVENTORYENTITY_DELETE')")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = {"application/json"})
	public void delete(@PathVariable String id) {

    	FindInventoryByIdOutput output = _inventoryAppService.findById(Integer.valueOf(id));
    	if(output == null) {
    		throw new EntityNotFoundException(String.format("There does not exist a inventory with a id=%s", id));
    	}	

    	_inventoryAppService.delete(Integer.valueOf(id));
    }


	// ------------ Update inventory ------------
    @PreAuthorize("hasAnyAuthority('INVENTORYENTITY_UPDATE')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<UpdateInventoryOutput> update(@PathVariable String id,  @RequestBody @Valid UpdateInventoryInput inventory) {

	    FindInventoryByIdOutput currentInventory = _inventoryAppService.findById(Integer.valueOf(id));
		if(currentInventory == null) {
			throw new EntityNotFoundException(String.format("Unable to update. Inventory with id=%s not found.", id));
		}

		inventory.setVersiono(currentInventory.getVersiono());
	    UpdateInventoryOutput output = _inventoryAppService.update(Integer.valueOf(id),inventory);
		if(output == null) {
    		throw new EntityNotFoundException("No record found");
    	}
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    

    @PreAuthorize("hasAnyAuthority('INVENTORYENTITY_READ')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<FindInventoryByIdOutput> findById(@PathVariable String id) {

    	FindInventoryByIdOutput output = _inventoryAppService.findById(Integer.valueOf(id));
        if(output == null) {
    		throw new EntityNotFoundException("Not found");
    	}
    	
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    @PreAuthorize("hasAnyAuthority('INVENTORYENTITY_READ')")
	@RequestMapping(method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<List<FindInventoryByIdOutput>> find(@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws EntityNotFoundException, MalformedURLException {

		if (offset == null) { offset = env.getProperty("comehere.offset.default"); }
		if (limit == null) { limit = env.getProperty("comehere.limit.default"); }

		Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

		return new ResponseEntity<>(_inventoryAppService.find(searchCriteria,Pageable), HttpStatus.OK);
	}
	
    @PreAuthorize("hasAnyAuthority('INVENTORYENTITY_READ')")
	@RequestMapping(value = "/{id}/products", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<GetProductsOutput> getProducts(@PathVariable String id) {
    	GetProductsOutput output= _inventoryAppService.getProducts(Integer.valueOf(id));
		if(output ==null) {
			throw new EntityNotFoundException("Not found");
	    }		

		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    @PreAuthorize("hasAnyAuthority('INVENTORYENTITY_READ')")
	@RequestMapping(value = "/{id}/suppliers", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<GetSuppliersOutput> getSuppliers(@PathVariable String id) {
    	GetSuppliersOutput output= _inventoryAppService.getSuppliers(Integer.valueOf(id));
		if(output ==null) {
			throw new EntityNotFoundException("Not found");
	    }		

		return new ResponseEntity<>(output, HttpStatus.OK);
	}
}


