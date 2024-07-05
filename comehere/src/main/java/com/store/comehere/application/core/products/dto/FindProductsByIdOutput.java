package com.store.comehere.application.core.products.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter @Setter
public class FindProductsByIdOutput {

  	private String category;
  	private LocalDateTime createdAt;
  	private String description;
  	private String name;
  	private BigDecimal price;
  	private Integer productId;
  	private LocalDateTime updatedAt;
	private Long versiono;
 
}

