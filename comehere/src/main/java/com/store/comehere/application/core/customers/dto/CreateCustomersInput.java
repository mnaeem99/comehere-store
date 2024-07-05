package com.store.comehere.application.core.customers.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateCustomersInput {

  	private String address;
  
  	private LocalDateTime createdAt;
  
  	@NotNull(message = "email Should not be null")
  	@Length(max = 100, message = "email must be less than 100 characters")
  	private String email;
  
  	@NotNull(message = "firstName Should not be null")
  	@Length(max = 50, message = "firstName must be less than 50 characters")
  	private String firstName;
  
  	@NotNull(message = "lastName Should not be null")
  	@Length(max = 50, message = "lastName must be less than 50 characters")
  	private String lastName;
  
  	@Length(max = 15, message = "phone must be less than 15 characters")
  	private String phone;
  
  	private LocalDateTime updatedAt;
  

}

