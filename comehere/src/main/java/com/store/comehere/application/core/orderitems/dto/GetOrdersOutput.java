package com.store.comehere.application.core.orderitems.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter @Setter
public class GetOrdersOutput {

 	private LocalDateTime orderDate;
 	private Integer orderId;
 	private String status;
 	private BigDecimal total;
  	private Integer orderItemsOrderItemId;

}

