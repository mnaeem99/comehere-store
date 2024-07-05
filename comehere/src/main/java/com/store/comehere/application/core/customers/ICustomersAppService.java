package com.store.comehere.application.core.customers;

import org.springframework.data.domain.Pageable;
import com.store.comehere.application.core.customers.dto.*;
import com.store.comehere.commons.search.SearchCriteria;
import java.net.MalformedURLException;
import java.util.*;

public interface ICustomersAppService {
	
	//CRUD Operations
	CreateCustomersOutput create(CreateCustomersInput customers);

    void delete(Integer id);

    UpdateCustomersOutput update(Integer id, UpdateCustomersInput input);

    FindCustomersByIdOutput findById(Integer id);


    List<FindCustomersByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException;
    
    //Join Column Parsers

	Map<String,String> parseOrdersJoinColumn(String keysString);
}

