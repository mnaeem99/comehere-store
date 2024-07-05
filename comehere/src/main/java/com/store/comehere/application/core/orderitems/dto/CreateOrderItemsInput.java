package com.store.comehere.application.core.orderitems.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateOrderItemsInput {

  	@NotNull(message = "price Should not be null")
  	private BigDecimal price;
  
  	@NotNull(message = "quantity Should not be null")
  	private Integer quantity;
  
  	private Integer orderId;
  	private Integer productId;

}

