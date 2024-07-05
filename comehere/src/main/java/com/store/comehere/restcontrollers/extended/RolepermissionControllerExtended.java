package com.store.comehere.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.store.comehere.restcontrollers.core.RolepermissionController;
import com.store.comehere.application.extended.authorization.rolepermission.IRolepermissionAppServiceExtended;

import com.store.comehere.application.extended.authorization.permission.IPermissionAppServiceExtended;
import com.store.comehere.application.extended.authorization.role.IRoleAppServiceExtended;
import org.springframework.core.env.Environment;
import com.store.comehere.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/rolepermission/extended")
public class RolepermissionControllerExtended extends RolepermissionController {

		public RolepermissionControllerExtended(IRolepermissionAppServiceExtended rolepermissionAppServiceExtended, IPermissionAppServiceExtended permissionAppServiceExtended, IRoleAppServiceExtended roleAppServiceExtended,
	     LoggingHelper helper, Environment env) {
		super(
		rolepermissionAppServiceExtended,
		
    	permissionAppServiceExtended,
		
    	roleAppServiceExtended,
		helper, env);
	}

	//Add your custom code here

}

