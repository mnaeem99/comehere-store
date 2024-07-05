package com.store.comehere.application.core.authorization.users.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateUsersOutput {

    private String emailAddress;
    private String firstName;
    private Boolean isActive;
    private Boolean isEmailConfirmed;
    private String lastName;
    private String phoneNumber;
    private Long userId;
    private String username;
	private String theme;
    private String language;

}

