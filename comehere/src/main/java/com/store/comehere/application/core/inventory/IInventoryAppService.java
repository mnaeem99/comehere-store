package com.store.comehere.application.core.inventory;

import org.springframework.data.domain.Pageable;
import com.store.comehere.application.core.inventory.dto.*;
import com.store.comehere.commons.search.SearchCriteria;
import java.net.MalformedURLException;
import java.util.*;

public interface IInventoryAppService {
	
	//CRUD Operations
	CreateInventoryOutput create(CreateInventoryInput inventory);

    void delete(Integer id);

    UpdateInventoryOutput update(Integer id, UpdateInventoryInput input);

    FindInventoryByIdOutput findById(Integer id);


    List<FindInventoryByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException;
	//Relationship Operations
	//Relationship Operations
    
    GetProductsOutput getProducts(Integer inventoryid);
    
    GetSuppliersOutput getSuppliers(Integer inventoryid);
    
    //Join Column Parsers
}

