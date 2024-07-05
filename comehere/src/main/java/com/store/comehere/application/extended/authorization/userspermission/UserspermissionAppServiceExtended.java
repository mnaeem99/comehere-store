package com.store.comehere.application.extended.authorization.userspermission;

import org.springframework.stereotype.Service;
import com.store.comehere.application.core.authorization.userspermission.UserspermissionAppService;

import com.store.comehere.domain.extended.authorization.userspermission.IUserspermissionRepositoryExtended;
import com.store.comehere.domain.extended.authorization.permission.IPermissionRepositoryExtended;
import com.store.comehere.domain.extended.authorization.users.IUsersRepositoryExtended;
import com.store.comehere.commons.logging.LoggingHelper;

@Service("userspermissionAppServiceExtended")
public class UserspermissionAppServiceExtended extends UserspermissionAppService implements IUserspermissionAppServiceExtended {

	public UserspermissionAppServiceExtended(IUserspermissionRepositoryExtended userspermissionRepositoryExtended,
				IPermissionRepositoryExtended permissionRepositoryExtended,IUsersRepositoryExtended usersRepositoryExtended,IUserspermissionMapperExtended mapper,LoggingHelper logHelper) {

		super(userspermissionRepositoryExtended,
		permissionRepositoryExtended,usersRepositoryExtended,mapper,logHelper);

	}

 	//Add your custom code here
 
}

