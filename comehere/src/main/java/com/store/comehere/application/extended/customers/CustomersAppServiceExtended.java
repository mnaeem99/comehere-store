package com.store.comehere.application.extended.customers;

import org.springframework.stereotype.Service;
import com.store.comehere.application.core.customers.CustomersAppService;

import com.store.comehere.domain.extended.customers.ICustomersRepositoryExtended;
import com.store.comehere.commons.logging.LoggingHelper;

@Service("customersAppServiceExtended")
public class CustomersAppServiceExtended extends CustomersAppService implements ICustomersAppServiceExtended {

	public CustomersAppServiceExtended(ICustomersRepositoryExtended customersRepositoryExtended,
				ICustomersMapperExtended mapper,LoggingHelper logHelper) {

		super(customersRepositoryExtended,
		mapper,logHelper);

	}

 	//Add your custom code here
 
}

