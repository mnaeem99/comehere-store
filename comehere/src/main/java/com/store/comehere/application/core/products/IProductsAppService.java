package com.store.comehere.application.core.products;

import org.springframework.data.domain.Pageable;
import com.store.comehere.application.core.products.dto.*;
import com.store.comehere.commons.search.SearchCriteria;
import java.net.MalformedURLException;
import java.util.*;

public interface IProductsAppService {
	
	//CRUD Operations
	CreateProductsOutput create(CreateProductsInput products);

    void delete(Integer id);

    UpdateProductsOutput update(Integer id, UpdateProductsInput input);

    FindProductsByIdOutput findById(Integer id);


    List<FindProductsByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException;
    
    //Join Column Parsers

	Map<String,String> parseInventorysJoinColumn(String keysString);

	Map<String,String> parseOrderItemsJoinColumn(String keysString);
}

