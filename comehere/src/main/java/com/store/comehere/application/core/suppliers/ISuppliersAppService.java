package com.store.comehere.application.core.suppliers;

import org.springframework.data.domain.Pageable;
import com.store.comehere.application.core.suppliers.dto.*;
import com.store.comehere.commons.search.SearchCriteria;
import java.net.MalformedURLException;
import java.util.*;

public interface ISuppliersAppService {
	
	//CRUD Operations
	CreateSuppliersOutput create(CreateSuppliersInput suppliers);

    void delete(Integer id);

    UpdateSuppliersOutput update(Integer id, UpdateSuppliersInput input);

    FindSuppliersByIdOutput findById(Integer id);


    List<FindSuppliersByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException;
    
    //Join Column Parsers

	Map<String,String> parseInventorysJoinColumn(String keysString);
}

