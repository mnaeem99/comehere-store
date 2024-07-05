package com.store.comehere.application.extended.authorization.usersrole;

import org.springframework.stereotype.Service;
import com.store.comehere.application.core.authorization.usersrole.UsersroleAppService;

import com.store.comehere.domain.extended.authorization.usersrole.IUsersroleRepositoryExtended;
import com.store.comehere.domain.extended.authorization.role.IRoleRepositoryExtended;
import com.store.comehere.domain.extended.authorization.users.IUsersRepositoryExtended;
import com.store.comehere.commons.logging.LoggingHelper;

@Service("usersroleAppServiceExtended")
public class UsersroleAppServiceExtended extends UsersroleAppService implements IUsersroleAppServiceExtended {

	public UsersroleAppServiceExtended(IUsersroleRepositoryExtended usersroleRepositoryExtended,
				IRoleRepositoryExtended roleRepositoryExtended,IUsersRepositoryExtended usersRepositoryExtended,IUsersroleMapperExtended mapper,LoggingHelper logHelper) {

		super(usersroleRepositoryExtended,
		roleRepositoryExtended,usersRepositoryExtended,mapper,logHelper);

	}

 	//Add your custom code here
 
}

