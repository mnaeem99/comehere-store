package com.store.comehere.application.extended.authorization.role;

import org.springframework.stereotype.Service;
import com.store.comehere.application.core.authorization.role.RoleAppService;

import com.store.comehere.domain.extended.authorization.role.IRoleRepositoryExtended;
import com.store.comehere.commons.logging.LoggingHelper;

@Service("roleAppServiceExtended")
public class RoleAppServiceExtended extends RoleAppService implements IRoleAppServiceExtended {

	public RoleAppServiceExtended(IRoleRepositoryExtended roleRepositoryExtended,
				IRoleMapperExtended mapper,LoggingHelper logHelper) {

		super(roleRepositoryExtended,
		mapper,logHelper);

	}

 	//Add your custom code here
 
}

