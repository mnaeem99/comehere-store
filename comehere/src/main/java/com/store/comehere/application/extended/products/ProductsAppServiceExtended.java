package com.store.comehere.application.extended.products;

import org.springframework.stereotype.Service;
import com.store.comehere.application.core.products.ProductsAppService;

import com.store.comehere.domain.extended.products.IProductsRepositoryExtended;
import com.store.comehere.commons.logging.LoggingHelper;

@Service("productsAppServiceExtended")
public class ProductsAppServiceExtended extends ProductsAppService implements IProductsAppServiceExtended {

	public ProductsAppServiceExtended(IProductsRepositoryExtended productsRepositoryExtended,
				IProductsMapperExtended mapper,LoggingHelper logHelper) {

		super(productsRepositoryExtended,
		mapper,logHelper);

	}

 	//Add your custom code here
 
}

