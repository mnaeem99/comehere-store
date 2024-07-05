package com.store.comehere.application.core.inventory;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.store.comehere.domain.core.products.Products;
import com.store.comehere.domain.core.suppliers.Suppliers;
import com.store.comehere.application.core.inventory.dto.*;
import com.store.comehere.domain.core.inventory.Inventory;
import java.time.*;

@Mapper(componentModel = "spring")
public interface IInventoryMapper {
   Inventory createInventoryInputToInventory(CreateInventoryInput inventoryDto);
   
   @Mappings({ 
   @Mapping(source = "entity.products.productId", target = "productId"),                   
   @Mapping(source = "entity.products.productId", target = "productsDescriptiveField"),                    
   @Mapping(source = "entity.suppliers.supplierId", target = "supplierId"),                   
   @Mapping(source = "entity.suppliers.supplierId", target = "suppliersDescriptiveField"),                    
   }) 
   CreateInventoryOutput inventoryToCreateInventoryOutput(Inventory entity);
   
    Inventory updateInventoryInputToInventory(UpdateInventoryInput inventoryDto);
    
    @Mappings({ 
    @Mapping(source = "entity.products.productId", target = "productId"),                   
    @Mapping(source = "entity.products.productId", target = "productsDescriptiveField"),                    
    @Mapping(source = "entity.suppliers.supplierId", target = "supplierId"),                   
    @Mapping(source = "entity.suppliers.supplierId", target = "suppliersDescriptiveField"),                    
   	}) 
   	UpdateInventoryOutput inventoryToUpdateInventoryOutput(Inventory entity);
   	@Mappings({ 
   	@Mapping(source = "entity.products.productId", target = "productId"),                   
   	@Mapping(source = "entity.products.productId", target = "productsDescriptiveField"),                    
   	@Mapping(source = "entity.suppliers.supplierId", target = "supplierId"),                   
   	@Mapping(source = "entity.suppliers.supplierId", target = "suppliersDescriptiveField"),                    
   	}) 
   	FindInventoryByIdOutput inventoryToFindInventoryByIdOutput(Inventory entity);


   @Mappings({
   @Mapping(source = "foundInventory.inventoryId", target = "inventoryInventoryId"),
   })
   GetProductsOutput productsToGetProductsOutput(Products products, Inventory foundInventory);
   
   @Mappings({
   @Mapping(source = "foundInventory.inventoryId", target = "inventoryInventoryId"),
   })
   GetSuppliersOutput suppliersToGetSuppliersOutput(Suppliers suppliers, Inventory foundInventory);
   
}

