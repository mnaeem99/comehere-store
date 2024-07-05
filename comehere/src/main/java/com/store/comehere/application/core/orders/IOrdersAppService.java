package com.store.comehere.application.core.orders;

import org.springframework.data.domain.Pageable;
import com.store.comehere.application.core.orders.dto.*;
import com.store.comehere.commons.search.SearchCriteria;
import java.net.MalformedURLException;
import java.util.*;

public interface IOrdersAppService {
	
	//CRUD Operations
	CreateOrdersOutput create(CreateOrdersInput orders);

    void delete(Integer id);

    UpdateOrdersOutput update(Integer id, UpdateOrdersInput input);

    FindOrdersByIdOutput findById(Integer id);


    List<FindOrdersByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException;
	//Relationship Operations
    
    GetCustomersOutput getCustomers(Integer ordersid);
    
    //Join Column Parsers

	Map<String,String> parseOrderItemsJoinColumn(String keysString);
}

