package com.store.comehere.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.store.comehere.restcontrollers.core.OrdersController;
import com.store.comehere.application.extended.orders.IOrdersAppServiceExtended;

import com.store.comehere.application.extended.customers.ICustomersAppServiceExtended;
import com.store.comehere.application.extended.orderitems.IOrderItemsAppServiceExtended;
import org.springframework.core.env.Environment;
import com.store.comehere.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/orders/extended")
public class OrdersControllerExtended extends OrdersController {

		public OrdersControllerExtended(IOrdersAppServiceExtended ordersAppServiceExtended, ICustomersAppServiceExtended customersAppServiceExtended, IOrderItemsAppServiceExtended orderItemsAppServiceExtended,
	     LoggingHelper helper, Environment env) {
		super(
		ordersAppServiceExtended,
		
    	customersAppServiceExtended,
		
    	orderItemsAppServiceExtended,
		helper, env);
	}

	//Add your custom code here

}

