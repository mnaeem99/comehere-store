package com.store.comehere.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.store.comehere.restcontrollers.core.SuppliersController;
import com.store.comehere.application.extended.suppliers.ISuppliersAppServiceExtended;

import com.store.comehere.application.extended.inventory.IInventoryAppServiceExtended;
import org.springframework.core.env.Environment;
import com.store.comehere.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/suppliers/extended")
public class SuppliersControllerExtended extends SuppliersController {

		public SuppliersControllerExtended(ISuppliersAppServiceExtended suppliersAppServiceExtended, IInventoryAppServiceExtended inventoryAppServiceExtended,
	     LoggingHelper helper, Environment env) {
		super(
		suppliersAppServiceExtended,
		
    	inventoryAppServiceExtended,
		helper, env);
	}

	//Add your custom code here

}

