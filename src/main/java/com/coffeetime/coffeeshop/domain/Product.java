package com.coffeetime.coffeeshop.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name")
	@NotEmpty(message = "Product name cannot be empty")
	private String name;
	
	@Column(name = "amount")
	@NotNull(message = "Product price cannot be empty")
	@Min(value = 0, message = "Product price must be greater than or equal to 0")
	private BigDecimal amount;	
}
