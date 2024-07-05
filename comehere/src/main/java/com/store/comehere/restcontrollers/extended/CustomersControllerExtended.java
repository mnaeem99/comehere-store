package com.store.comehere.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.store.comehere.restcontrollers.core.CustomersController;
import com.store.comehere.application.extended.customers.ICustomersAppServiceExtended;

import com.store.comehere.application.extended.orders.IOrdersAppServiceExtended;
import org.springframework.core.env.Environment;
import com.store.comehere.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/customers/extended")
public class CustomersControllerExtended extends CustomersController {

		public CustomersControllerExtended(ICustomersAppServiceExtended customersAppServiceExtended, IOrdersAppServiceExtended ordersAppServiceExtended,
	     LoggingHelper helper, Environment env) {
		super(
		customersAppServiceExtended,
		
    	ordersAppServiceExtended,
		helper, env);
	}

	//Add your custom code here

}

