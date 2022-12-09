package com.coffeetime.coffeeshop.domain;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	

	@Column(unique = true) // TODO: Create a validator for a custom message
	@NotNull(message = "Name cannot be null")
	@NotEmpty(message = "Name cannot be empty")
	private String name;
	
	@Column(name = "amount")
	@Min(value = 1, message = "Product price must be greater than or equal to 1")
	private BigDecimal amount;	
}
