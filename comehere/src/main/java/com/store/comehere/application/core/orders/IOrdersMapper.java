package com.store.comehere.application.core.orders;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.store.comehere.domain.core.customers.Customers;
import com.store.comehere.application.core.orders.dto.*;
import com.store.comehere.domain.core.orders.Orders;
import java.time.*;

@Mapper(componentModel = "spring")
public interface IOrdersMapper {
   Orders createOrdersInputToOrders(CreateOrdersInput ordersDto);
   
   @Mappings({ 
   @Mapping(source = "entity.customers.customerId", target = "customerId"),                   
   @Mapping(source = "entity.customers.email", target = "customersDescriptiveField"),                    
   }) 
   CreateOrdersOutput ordersToCreateOrdersOutput(Orders entity);
   
    Orders updateOrdersInputToOrders(UpdateOrdersInput ordersDto);
    
    @Mappings({ 
    @Mapping(source = "entity.customers.customerId", target = "customerId"),                   
    @Mapping(source = "entity.customers.email", target = "customersDescriptiveField"),                    
   	}) 
   	UpdateOrdersOutput ordersToUpdateOrdersOutput(Orders entity);
   	@Mappings({ 
   	@Mapping(source = "entity.customers.customerId", target = "customerId"),                   
   	@Mapping(source = "entity.customers.email", target = "customersDescriptiveField"),                    
   	}) 
   	FindOrdersByIdOutput ordersToFindOrdersByIdOutput(Orders entity);


   @Mappings({
   @Mapping(source = "foundOrders.orderId", target = "ordersOrderId"),
   })
   GetCustomersOutput customersToGetCustomersOutput(Customers customers, Orders foundOrders);
   
}

