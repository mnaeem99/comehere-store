package com.store.comehere.application.extended.authorization.permission;

import org.springframework.stereotype.Service;
import com.store.comehere.application.core.authorization.permission.PermissionAppService;

import com.store.comehere.domain.extended.authorization.permission.IPermissionRepositoryExtended;
import com.store.comehere.commons.logging.LoggingHelper;

@Service("permissionAppServiceExtended")
public class PermissionAppServiceExtended extends PermissionAppService implements IPermissionAppServiceExtended {

	public PermissionAppServiceExtended(IPermissionRepositoryExtended permissionRepositoryExtended,
				IPermissionMapperExtended mapper,LoggingHelper logHelper) {

		super(permissionRepositoryExtended,
		mapper,logHelper);

	}

 	//Add your custom code here
 
}

