package com.store.comehere.application.extended.authorization.users;

import org.springframework.stereotype.Service;
import com.store.comehere.application.core.authorization.users.UsersAppService;

import com.store.comehere.domain.extended.authorization.users.IUsersRepositoryExtended;
import com.store.comehere.domain.core.authorization.userspreference.IUserspreferenceRepository;
import com.store.comehere.commons.logging.LoggingHelper;

@Service("usersAppServiceExtended")
public class UsersAppServiceExtended extends UsersAppService implements IUsersAppServiceExtended {

	public UsersAppServiceExtended(IUsersRepositoryExtended usersRepositoryExtended,
				IUserspreferenceRepository userspreferenceRepository,IUsersMapperExtended mapper,LoggingHelper logHelper) {

		super(usersRepositoryExtended,
		userspreferenceRepository,mapper,logHelper);

	}

 	//Add your custom code here
 
}

