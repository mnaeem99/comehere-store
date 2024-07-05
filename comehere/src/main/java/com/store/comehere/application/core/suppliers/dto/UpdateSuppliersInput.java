package com.store.comehere.application.core.suppliers.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateSuppliersInput {

  	private String address;
  	
 	@Length(max = 100, message = "contactEmail must be less than 100 characters")
  	private String contactEmail;
  	
 	@Length(max = 50, message = "contactName must be less than 50 characters")
  	private String contactName;
  	
 	@Length(max = 15, message = "contactPhone must be less than 15 characters")
  	private String contactPhone;
  	
  	private LocalDateTime createdAt;
  	
  	@NotNull(message = "name Should not be null")
 	@Length(max = 100, message = "name must be less than 100 characters")
  	private String name;
  	
  	@NotNull(message = "supplierId Should not be null")
  	private Integer supplierId;
  	
  	private LocalDateTime updatedAt;
  	
  	private Long versiono;
  
}

