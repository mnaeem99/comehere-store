package com.store.comehere.application.core.orderitems.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter @Setter
public class UpdateOrderItemsInput {

  	@NotNull(message = "orderItemId Should not be null")
  	private Integer orderItemId;
  	
  	@NotNull(message = "price Should not be null")
  	private BigDecimal price;
  	
  	@NotNull(message = "quantity Should not be null")
  	private Integer quantity;
  	
  	private Integer orderId;
  	private Integer productId;
  	private Long versiono;
  
}

