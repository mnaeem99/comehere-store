package com.store.comehere.application.core.customers.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateCustomersOutput {

  	private String address;
  	private LocalDateTime createdAt;
  	private Integer customerId;
  	private String email;
  	private String firstName;
  	private String lastName;
  	private String phone;
  	private LocalDateTime updatedAt;

}
