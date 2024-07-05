package com.store.comehere.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.store.comehere.restcontrollers.core.PermissionController;
import com.store.comehere.application.extended.authorization.permission.IPermissionAppServiceExtended;

import com.store.comehere.application.extended.authorization.rolepermission.IRolepermissionAppServiceExtended;
import com.store.comehere.application.extended.authorization.userspermission.IUserspermissionAppServiceExtended;
import org.springframework.core.env.Environment;
import com.store.comehere.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/permission/extended")
public class PermissionControllerExtended extends PermissionController {

		public PermissionControllerExtended(IPermissionAppServiceExtended permissionAppServiceExtended, IRolepermissionAppServiceExtended rolepermissionAppServiceExtended, IUserspermissionAppServiceExtended userspermissionAppServiceExtended,
	     LoggingHelper helper, Environment env) {
		super(
		permissionAppServiceExtended,
		
    	rolepermissionAppServiceExtended,
		
    	userspermissionAppServiceExtended,
		helper, env);
	}

	//Add your custom code here

}

