package com.store.comehere.application.core.orders.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter @Setter
public class UpdateOrdersInput {

  	private LocalDateTime orderDate;
  	
  	@NotNull(message = "orderId Should not be null")
  	private Integer orderId;
  	
  	@NotNull(message = "status Should not be null")
 	@Length(max = 50, message = "status must be less than 50 characters")
  	private String status;
  	
  	@NotNull(message = "total Should not be null")
  	private BigDecimal total;
  	
  	private Integer customerId;
  	private Long versiono;
  
}

