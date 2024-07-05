package com.store.comehere.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.store.comehere.restcontrollers.core.InventoryController;
import com.store.comehere.application.extended.inventory.IInventoryAppServiceExtended;

import com.store.comehere.application.extended.products.IProductsAppServiceExtended;
import com.store.comehere.application.extended.suppliers.ISuppliersAppServiceExtended;
import org.springframework.core.env.Environment;
import com.store.comehere.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/inventory/extended")
public class InventoryControllerExtended extends InventoryController {

		public InventoryControllerExtended(IInventoryAppServiceExtended inventoryAppServiceExtended, IProductsAppServiceExtended productsAppServiceExtended, ISuppliersAppServiceExtended suppliersAppServiceExtended,
	     LoggingHelper helper, Environment env) {
		super(
		inventoryAppServiceExtended,
		
    	productsAppServiceExtended,
		
    	suppliersAppServiceExtended,
		helper, env);
	}

	//Add your custom code here

}

