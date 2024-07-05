package com.store.comehere.application.extended.suppliers;

import org.springframework.stereotype.Service;
import com.store.comehere.application.core.suppliers.SuppliersAppService;

import com.store.comehere.domain.extended.suppliers.ISuppliersRepositoryExtended;
import com.store.comehere.commons.logging.LoggingHelper;

@Service("suppliersAppServiceExtended")
public class SuppliersAppServiceExtended extends SuppliersAppService implements ISuppliersAppServiceExtended {

	public SuppliersAppServiceExtended(ISuppliersRepositoryExtended suppliersRepositoryExtended,
				ISuppliersMapperExtended mapper,LoggingHelper logHelper) {

		super(suppliersRepositoryExtended,
		mapper,logHelper);

	}

 	//Add your custom code here
 
}

