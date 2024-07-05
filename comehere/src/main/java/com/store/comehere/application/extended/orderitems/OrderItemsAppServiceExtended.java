package com.store.comehere.application.extended.orderitems;

import org.springframework.stereotype.Service;
import com.store.comehere.application.core.orderitems.OrderItemsAppService;

import com.store.comehere.domain.extended.orderitems.IOrderItemsRepositoryExtended;
import com.store.comehere.domain.extended.orders.IOrdersRepositoryExtended;
import com.store.comehere.domain.extended.products.IProductsRepositoryExtended;
import com.store.comehere.commons.logging.LoggingHelper;

@Service("orderItemsAppServiceExtended")
public class OrderItemsAppServiceExtended extends OrderItemsAppService implements IOrderItemsAppServiceExtended {

	public OrderItemsAppServiceExtended(IOrderItemsRepositoryExtended orderItemsRepositoryExtended,
				IOrdersRepositoryExtended ordersRepositoryExtended,IProductsRepositoryExtended productsRepositoryExtended,IOrderItemsMapperExtended mapper,LoggingHelper logHelper) {

		super(orderItemsRepositoryExtended,
		ordersRepositoryExtended,productsRepositoryExtended,mapper,logHelper);

	}

 	//Add your custom code here
 
}

