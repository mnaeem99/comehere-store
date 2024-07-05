package com.store.comehere.application.core.inventory;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import com.store.comehere.application.core.inventory.dto.*;
import com.store.comehere.domain.core.inventory.IInventoryRepository;
import com.store.comehere.domain.core.inventory.QInventory;
import com.store.comehere.domain.core.inventory.Inventory;
import com.store.comehere.domain.core.products.IProductsRepository;
import com.store.comehere.domain.core.products.Products;
import com.store.comehere.domain.core.suppliers.ISuppliersRepository;
import com.store.comehere.domain.core.suppliers.Suppliers;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page; 
import org.springframework.data.domain.Pageable; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import com.store.comehere.commons.search.*;
import com.store.comehere.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import java.net.MalformedURLException;
import java.time.*;
import java.util.*;
import javax.persistence.EntityNotFoundException;

@Service("inventoryAppService")
@RequiredArgsConstructor
public class InventoryAppService implements IInventoryAppService {
    
	@Qualifier("inventoryRepository")
	@NonNull protected final IInventoryRepository _inventoryRepository;

	
    @Qualifier("productsRepository")
	@NonNull protected final IProductsRepository _productsRepository;

    @Qualifier("suppliersRepository")
	@NonNull protected final ISuppliersRepository _suppliersRepository;

	@Qualifier("IInventoryMapperImpl")
	@NonNull protected final IInventoryMapper mapper;

	@NonNull protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
	public CreateInventoryOutput create(CreateInventoryInput input) {

		Inventory inventory = mapper.createInventoryInputToInventory(input);
		Products foundProducts = null;
		Suppliers foundSuppliers = null;
	  	if(input.getProductId()!=null) {
			foundProducts = _productsRepository.findById(input.getProductId()).orElse(null);
			
			if(foundProducts!=null) {
				foundProducts.addInventorys(inventory);
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
	  	if(input.getSupplierId()!=null) {
			foundSuppliers = _suppliersRepository.findById(input.getSupplierId()).orElse(null);
			
			if(foundSuppliers!=null) {
				foundSuppliers.addInventorys(inventory);
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}

		Inventory createdInventory = _inventoryRepository.save(inventory);
		return mapper.inventoryToCreateInventoryOutput(createdInventory);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public UpdateInventoryOutput update(Integer inventoryId, UpdateInventoryInput input) {

		Inventory existing = _inventoryRepository.findById(inventoryId).orElseThrow(() -> new EntityNotFoundException("Inventory not found"));

		Inventory inventory = mapper.updateInventoryInputToInventory(input);
		Products foundProducts = null;
		Suppliers foundSuppliers = null;
        
	  	if(input.getProductId()!=null) { 
			foundProducts = _productsRepository.findById(input.getProductId()).orElse(null);
		
			if(foundProducts!=null) {
				foundProducts.addInventorys(inventory);
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
        
	  	if(input.getSupplierId()!=null) { 
			foundSuppliers = _suppliersRepository.findById(input.getSupplierId()).orElse(null);
		
			if(foundSuppliers!=null) {
				foundSuppliers.addInventorys(inventory);
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
		
		Inventory updatedInventory = _inventoryRepository.save(inventory);
		return mapper.inventoryToUpdateInventoryOutput(updatedInventory);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Integer inventoryId) {

		Inventory existing = _inventoryRepository.findById(inventoryId).orElseThrow(() -> new EntityNotFoundException("Inventory not found"));
	 	
        if(existing.getProducts() !=null)
        {
        existing.getProducts().removeInventorys(existing);
        }
        if(existing.getSuppliers() !=null)
        {
        existing.getSuppliers().removeInventorys(existing);
        }
        if(existing !=null) {
			_inventoryRepository.delete(existing);
		}
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public FindInventoryByIdOutput findById(Integer inventoryId) {

		Inventory foundInventory = _inventoryRepository.findById(inventoryId).orElse(null);
		if (foundInventory == null)  
			return null; 
 	   
 	    return mapper.inventoryToFindInventoryByIdOutput(foundInventory);
	}

    //Products
	// ReST API Call - GET /inventory/1/products
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public GetProductsOutput getProducts(Integer inventoryId) {

		Inventory foundInventory = _inventoryRepository.findById(inventoryId).orElse(null);
		if (foundInventory == null) {
			logHelper.getLogger().error("There does not exist a inventory wth a id='{}'", inventoryId);
			return null;
		}
		Products re = foundInventory.getProducts();
		return mapper.productsToGetProductsOutput(re, foundInventory);
	}
	
    //Suppliers
	// ReST API Call - GET /inventory/1/suppliers
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public GetSuppliersOutput getSuppliers(Integer inventoryId) {

		Inventory foundInventory = _inventoryRepository.findById(inventoryId).orElse(null);
		if (foundInventory == null) {
			logHelper.getLogger().error("There does not exist a inventory wth a id='{}'", inventoryId);
			return null;
		}
		Suppliers re = foundInventory.getSuppliers();
		return mapper.suppliersToGetSuppliersOutput(re, foundInventory);
	}
	
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<FindInventoryByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException  {

		Page<Inventory> foundInventory = _inventoryRepository.findAll(search(search), pageable);
		List<Inventory> inventoryList = foundInventory.getContent();
		Iterator<Inventory> inventoryIterator = inventoryList.iterator(); 
		List<FindInventoryByIdOutput> output = new ArrayList<>();

		while (inventoryIterator.hasNext()) {
		Inventory inventory = inventoryIterator.next();
 	    output.add(mapper.inventoryToFindInventoryByIdOutput(inventory));
		}
		return output;
	}
	
	protected BooleanBuilder search(SearchCriteria search) throws MalformedURLException {

		QInventory inventory= QInventory.inventoryEntity;
		if(search != null) {
			Map<String,SearchFields> map = new HashMap<>();
			for(SearchFields fieldDetails: search.getFields())
			{
				map.put(fieldDetails.getFieldName(),fieldDetails);
			}
			List<String> keysList = new ArrayList<String>(map.keySet());
			checkProperties(keysList);
			return searchKeyValuePair(inventory, map,search.getJoinColumns());
		}
		return null;
	}
	
	protected void checkProperties(List<String> list) throws MalformedURLException  {
		for (int i = 0; i < list.size(); i++) {
			if(!(
		        list.get(i).replace("%20","").trim().equals("products") ||
				list.get(i).replace("%20","").trim().equals("productId") ||
		        list.get(i).replace("%20","").trim().equals("suppliers") ||
				list.get(i).replace("%20","").trim().equals("supplierId") ||
				list.get(i).replace("%20","").trim().equals("inventoryId") ||
				list.get(i).replace("%20","").trim().equals("lastRestocked") ||
				list.get(i).replace("%20","").trim().equals("quantity")
			)) 
			{
			 throw new MalformedURLException("Wrong URL Format: Property " + list.get(i) + " not found!" );
			}
		}
	}
	
	protected BooleanBuilder searchKeyValuePair(QInventory inventory, Map<String,SearchFields> map,Map<String,String> joinColumns) {
		BooleanBuilder builder = new BooleanBuilder();
        
		Iterator<Map.Entry<String, SearchFields>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, SearchFields> details = iterator.next();

			if(details.getKey().replace("%20","").trim().equals("inventoryId")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(inventory.inventoryId.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(inventory.inventoryId.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(inventory.inventoryId.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(inventory.inventoryId.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(inventory.inventoryId.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(inventory.inventoryId.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
			if(details.getKey().replace("%20","").trim().equals("lastRestocked")) {
				if(details.getValue().getOperator().equals("equals") && SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) !=null) {
					builder.and(inventory.lastRestocked.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) !=null) {
					builder.and(inventory.lastRestocked.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   LocalDateTime startLocalDateTime= SearchUtils.stringToLocalDateTime(details.getValue().getStartingValue());
				   LocalDateTime endLocalDateTime= SearchUtils.stringToLocalDateTime(details.getValue().getEndingValue());
				   if(startLocalDateTime!=null && endLocalDateTime!=null) {
					   builder.and(inventory.lastRestocked.between(startLocalDateTime,endLocalDateTime));
				   } else if(endLocalDateTime!=null) {
					   builder.and(inventory.lastRestocked.loe(endLocalDateTime));
                   } else if(startLocalDateTime!=null) {
                	   builder.and(inventory.lastRestocked.goe(startLocalDateTime));  
                   }
                }     
			}
			if(details.getKey().replace("%20","").trim().equals("quantity")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(inventory.quantity.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(inventory.quantity.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(inventory.quantity.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(inventory.quantity.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(inventory.quantity.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(inventory.quantity.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
	    
		     if(details.getKey().replace("%20","").trim().equals("products")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(inventory.products.productId.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(inventory.products.productId.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(inventory.products.productId.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(inventory.products.productId.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(inventory.products.productId.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(inventory.products.productId.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
		     if(details.getKey().replace("%20","").trim().equals("suppliers")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(inventory.suppliers.supplierId.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(inventory.suppliers.supplierId.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(inventory.suppliers.supplierId.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(inventory.suppliers.supplierId.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(inventory.suppliers.supplierId.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(inventory.suppliers.supplierId.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
		}
		
		for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
		if(joinCol != null && joinCol.getKey().equals("productId")) {
		    builder.and(inventory.products.productId.eq(Integer.parseInt(joinCol.getValue())));
		}
        
        }
		for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
		if(joinCol != null && joinCol.getKey().equals("supplierId")) {
		    builder.and(inventory.suppliers.supplierId.eq(Integer.parseInt(joinCol.getValue())));
		}
        
        }
		return builder;
	}
	
    
    
}



