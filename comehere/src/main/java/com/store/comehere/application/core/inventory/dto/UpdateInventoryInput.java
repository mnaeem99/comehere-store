package com.store.comehere.application.core.inventory.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateInventoryInput {

  	@NotNull(message = "inventoryId Should not be null")
  	private Integer inventoryId;
  	
  	private LocalDateTime lastRestocked;
  	
  	@NotNull(message = "quantity Should not be null")
  	private Integer quantity;
  	
  	private Integer productId;
  	private Integer supplierId;
  	private Long versiono;
  
}

