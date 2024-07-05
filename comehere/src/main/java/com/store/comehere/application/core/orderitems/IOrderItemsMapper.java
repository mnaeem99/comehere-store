package com.store.comehere.application.core.orderitems;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.store.comehere.domain.core.orders.Orders;
import com.store.comehere.domain.core.products.Products;
import com.store.comehere.application.core.orderitems.dto.*;
import com.store.comehere.domain.core.orderitems.OrderItems;
import java.time.*;

@Mapper(componentModel = "spring")
public interface IOrderItemsMapper {
   OrderItems createOrderItemsInputToOrderItems(CreateOrderItemsInput orderItemsDto);
   
   @Mappings({ 
   @Mapping(source = "entity.orders.orderId", target = "orderId"),                   
   @Mapping(source = "entity.orders.orderId", target = "ordersDescriptiveField"),                    
   @Mapping(source = "entity.products.productId", target = "productId"),                   
   @Mapping(source = "entity.products.productId", target = "productsDescriptiveField"),                    
   }) 
   CreateOrderItemsOutput orderItemsToCreateOrderItemsOutput(OrderItems entity);
   
    OrderItems updateOrderItemsInputToOrderItems(UpdateOrderItemsInput orderItemsDto);
    
    @Mappings({ 
    @Mapping(source = "entity.orders.orderId", target = "orderId"),                   
    @Mapping(source = "entity.orders.orderId", target = "ordersDescriptiveField"),                    
    @Mapping(source = "entity.products.productId", target = "productId"),                   
    @Mapping(source = "entity.products.productId", target = "productsDescriptiveField"),                    
   	}) 
   	UpdateOrderItemsOutput orderItemsToUpdateOrderItemsOutput(OrderItems entity);
   	@Mappings({ 
   	@Mapping(source = "entity.orders.orderId", target = "orderId"),                   
   	@Mapping(source = "entity.orders.orderId", target = "ordersDescriptiveField"),                    
   	@Mapping(source = "entity.products.productId", target = "productId"),                   
   	@Mapping(source = "entity.products.productId", target = "productsDescriptiveField"),                    
   	}) 
   	FindOrderItemsByIdOutput orderItemsToFindOrderItemsByIdOutput(OrderItems entity);


   @Mappings({
   @Mapping(source = "products.price", target = "price"),                  
   @Mapping(source = "foundOrderItems.orderItemId", target = "orderItemsOrderItemId"),
   })
   GetProductsOutput productsToGetProductsOutput(Products products, OrderItems foundOrderItems);
   
   @Mappings({
   @Mapping(source = "foundOrderItems.orderItemId", target = "orderItemsOrderItemId"),
   })
   GetOrdersOutput ordersToGetOrdersOutput(Orders orders, OrderItems foundOrderItems);
   
}

