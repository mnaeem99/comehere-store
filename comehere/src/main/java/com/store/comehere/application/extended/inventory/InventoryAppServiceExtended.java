package com.store.comehere.application.extended.inventory;

import org.springframework.stereotype.Service;
import com.store.comehere.application.core.inventory.InventoryAppService;

import com.store.comehere.domain.extended.inventory.IInventoryRepositoryExtended;
import com.store.comehere.domain.extended.products.IProductsRepositoryExtended;
import com.store.comehere.domain.extended.suppliers.ISuppliersRepositoryExtended;
import com.store.comehere.commons.logging.LoggingHelper;

@Service("inventoryAppServiceExtended")
public class InventoryAppServiceExtended extends InventoryAppService implements IInventoryAppServiceExtended {

	public InventoryAppServiceExtended(IInventoryRepositoryExtended inventoryRepositoryExtended,
				IProductsRepositoryExtended productsRepositoryExtended,ISuppliersRepositoryExtended suppliersRepositoryExtended,IInventoryMapperExtended mapper,LoggingHelper logHelper) {

		super(inventoryRepositoryExtended,
		productsRepositoryExtended,suppliersRepositoryExtended,mapper,logHelper);

	}

 	//Add your custom code here
 
}

