package com.store.comehere.application.extended.authorization.rolepermission;

import org.springframework.stereotype.Service;
import com.store.comehere.application.core.authorization.rolepermission.RolepermissionAppService;

import com.store.comehere.domain.extended.authorization.rolepermission.IRolepermissionRepositoryExtended;
import com.store.comehere.domain.extended.authorization.permission.IPermissionRepositoryExtended;
import com.store.comehere.domain.extended.authorization.role.IRoleRepositoryExtended;
import com.store.comehere.domain.extended.authorization.usersrole.IUsersroleRepositoryExtended;
import com.store.comehere.security.JWTAppService;
import com.store.comehere.commons.logging.LoggingHelper;

@Service("rolepermissionAppServiceExtended")
public class RolepermissionAppServiceExtended extends RolepermissionAppService implements IRolepermissionAppServiceExtended {

	public RolepermissionAppServiceExtended(JWTAppService jwtAppService,IUsersroleRepositoryExtended usersroleRepositoryExtended,IRolepermissionRepositoryExtended rolepermissionRepositoryExtended,
				IPermissionRepositoryExtended permissionRepositoryExtended,IRoleRepositoryExtended roleRepositoryExtended,IRolepermissionMapperExtended mapper,LoggingHelper logHelper) {

		super(jwtAppService, usersroleRepositoryExtended,rolepermissionRepositoryExtended,
		permissionRepositoryExtended,roleRepositoryExtended,mapper,logHelper);

	}

 	//Add your custom code here
 
}

