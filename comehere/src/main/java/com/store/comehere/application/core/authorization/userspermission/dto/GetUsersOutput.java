package com.store.comehere.application.core.authorization.userspermission.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetUsersOutput {

 	private String lastName;
 	private Boolean isActive;
 	private Long userId;
 	private String firstName;
 	private String emailAddress;
 	private String password;
 	private Boolean isEmailConfirmed;
 	private String username;
  	private Long userspermissionPermissionId;
  	private Long userspermissionUsersUserId;

}

