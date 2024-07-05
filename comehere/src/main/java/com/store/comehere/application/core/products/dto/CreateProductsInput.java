package com.store.comehere.application.core.products.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateProductsInput {

  	@Length(max = 50, message = "category must be less than 50 characters")
  	private String category;
  
  	private LocalDateTime createdAt;
  
  	private String description;
  
  	@NotNull(message = "name Should not be null")
  	@Length(max = 100, message = "name must be less than 100 characters")
  	private String name;
  
  	@NotNull(message = "price Should not be null")
  	private BigDecimal price;
  
  	private LocalDateTime updatedAt;
  

}

