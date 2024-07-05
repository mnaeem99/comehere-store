package com.store.comehere.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.store.comehere.restcontrollers.core.OrderItemsController;
import com.store.comehere.application.extended.orderitems.IOrderItemsAppServiceExtended;

import com.store.comehere.application.extended.orders.IOrdersAppServiceExtended;
import com.store.comehere.application.extended.products.IProductsAppServiceExtended;
import org.springframework.core.env.Environment;
import com.store.comehere.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/orderItems/extended")
public class OrderItemsControllerExtended extends OrderItemsController {

		public OrderItemsControllerExtended(IOrderItemsAppServiceExtended orderItemsAppServiceExtended, IOrdersAppServiceExtended ordersAppServiceExtended, IProductsAppServiceExtended productsAppServiceExtended,
	     LoggingHelper helper, Environment env) {
		super(
		orderItemsAppServiceExtended,
		
    	ordersAppServiceExtended,
		
    	productsAppServiceExtended,
		helper, env);
	}

	//Add your custom code here

}

