package com.coffeetime.coffeeshop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "topping")
@Data
@EqualsAndHashCode(callSuper = true)	
public class Topping extends Product {

	@Column(name = "ordered_count")
	private int orderedCount = 0;
}
