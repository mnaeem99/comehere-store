package com.store.comehere.application.extended.orders;

import org.springframework.stereotype.Service;
import com.store.comehere.application.core.orders.OrdersAppService;

import com.store.comehere.domain.extended.orders.IOrdersRepositoryExtended;
import com.store.comehere.domain.extended.customers.ICustomersRepositoryExtended;
import com.store.comehere.commons.logging.LoggingHelper;

@Service("ordersAppServiceExtended")
public class OrdersAppServiceExtended extends OrdersAppService implements IOrdersAppServiceExtended {

	public OrdersAppServiceExtended(IOrdersRepositoryExtended ordersRepositoryExtended,
				ICustomersRepositoryExtended customersRepositoryExtended,IOrdersMapperExtended mapper,LoggingHelper logHelper) {

		super(ordersRepositoryExtended,
		customersRepositoryExtended,mapper,logHelper);

	}

 	//Add your custom code here
 
}

