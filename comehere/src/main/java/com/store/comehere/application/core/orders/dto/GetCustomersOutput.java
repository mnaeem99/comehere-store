package com.store.comehere.application.core.orders.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetCustomersOutput {

 	private String address;
 	private LocalDateTime createdAt;
 	private Integer customerId;
 	private String email;
 	private String firstName;
 	private String lastName;
 	private String phone;
 	private LocalDateTime updatedAt;
  	private Integer ordersOrderId;

}

