package com.store.comehere.application.core.orderitems.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter @Setter
public class UpdateOrderItemsOutput {

  	private Integer orderItemId;
  	private BigDecimal price;
  	private Integer quantity;
  	private Integer orderId;
	private Integer ordersDescriptiveField;
  	private Integer productId;
	private Integer productsDescriptiveField;

}
