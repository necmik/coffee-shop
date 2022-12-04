package com.coffeetime.coffeeshop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "topping")
@Data
@EqualsAndHashCode(callSuper = true)	
public class Topping extends Product {

	@Column(name = "ordered_count")
	private int orderedCount;
}
