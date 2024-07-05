package com.store.comehere.application.core.inventory.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FindInventoryByIdOutput {

  	private Integer inventoryId;
  	private LocalDateTime lastRestocked;
  	private Integer quantity;
  	private Integer productId;
  	private Integer productsDescriptiveField;
  	private Integer supplierId;
  	private Integer suppliersDescriptiveField;
	private Long versiono;
 
}

