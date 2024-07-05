package com.store.comehere.application.core.customers;

import org.mapstruct.Mapper;
import com.store.comehere.application.core.customers.dto.*;
import com.store.comehere.domain.core.customers.Customers;
import java.time.*;

@Mapper(componentModel = "spring")
public interface ICustomersMapper {
   Customers createCustomersInputToCustomers(CreateCustomersInput customersDto);
   CreateCustomersOutput customersToCreateCustomersOutput(Customers entity);
   
    Customers updateCustomersInputToCustomers(UpdateCustomersInput customersDto);
    
   	UpdateCustomersOutput customersToUpdateCustomersOutput(Customers entity);
   	FindCustomersByIdOutput customersToFindCustomersByIdOutput(Customers entity);


}

