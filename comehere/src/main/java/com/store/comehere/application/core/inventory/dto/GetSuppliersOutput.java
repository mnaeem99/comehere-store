package com.store.comehere.application.core.inventory.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetSuppliersOutput {

 	private String address;
 	private String contactEmail;
 	private String contactName;
 	private String contactPhone;
 	private LocalDateTime createdAt;
 	private String name;
 	private Integer supplierId;
 	private LocalDateTime updatedAt;
  	private Integer inventoryInventoryId;

}

