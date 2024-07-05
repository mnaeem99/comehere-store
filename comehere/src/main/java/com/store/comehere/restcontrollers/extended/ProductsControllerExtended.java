package com.store.comehere.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.store.comehere.restcontrollers.core.ProductsController;
import com.store.comehere.application.extended.products.IProductsAppServiceExtended;

import com.store.comehere.application.extended.inventory.IInventoryAppServiceExtended;
import com.store.comehere.application.extended.orderitems.IOrderItemsAppServiceExtended;
import org.springframework.core.env.Environment;
import com.store.comehere.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/products/extended")
public class ProductsControllerExtended extends ProductsController {

		public ProductsControllerExtended(IProductsAppServiceExtended productsAppServiceExtended, IInventoryAppServiceExtended inventoryAppServiceExtended, IOrderItemsAppServiceExtended orderItemsAppServiceExtended,
	     LoggingHelper helper, Environment env) {
		super(
		productsAppServiceExtended,
		
    	inventoryAppServiceExtended,
		
    	orderItemsAppServiceExtended,
		helper, env);
	}

	//Add your custom code here

}

