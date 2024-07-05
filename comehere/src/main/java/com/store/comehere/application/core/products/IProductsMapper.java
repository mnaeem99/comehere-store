package com.store.comehere.application.core.products;

import org.mapstruct.Mapper;
import com.store.comehere.application.core.products.dto.*;
import com.store.comehere.domain.core.products.Products;
import java.time.*;

@Mapper(componentModel = "spring")
public interface IProductsMapper {
   Products createProductsInputToProducts(CreateProductsInput productsDto);
   CreateProductsOutput productsToCreateProductsOutput(Products entity);
   
    Products updateProductsInputToProducts(UpdateProductsInput productsDto);
    
   	UpdateProductsOutput productsToUpdateProductsOutput(Products entity);
   	FindProductsByIdOutput productsToFindProductsByIdOutput(Products entity);


}

