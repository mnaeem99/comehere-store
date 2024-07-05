package com.store.comehere.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.store.comehere.restcontrollers.core.RoleController;
import com.store.comehere.application.extended.authorization.role.IRoleAppServiceExtended;

import com.store.comehere.application.extended.authorization.rolepermission.IRolepermissionAppServiceExtended;
import com.store.comehere.application.extended.authorization.usersrole.IUsersroleAppServiceExtended;
import org.springframework.core.env.Environment;
import com.store.comehere.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/role/extended")
public class RoleControllerExtended extends RoleController {

		public RoleControllerExtended(IRoleAppServiceExtended roleAppServiceExtended, IRolepermissionAppServiceExtended rolepermissionAppServiceExtended, IUsersroleAppServiceExtended usersroleAppServiceExtended,
	     LoggingHelper helper, Environment env) {
		super(
		roleAppServiceExtended,
		
    	rolepermissionAppServiceExtended,
		
    	usersroleAppServiceExtended,
		helper, env);
	}

	//Add your custom code here

}

