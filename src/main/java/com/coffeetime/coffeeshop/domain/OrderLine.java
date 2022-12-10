package com.coffeetime.coffeeshop.domain;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * 
 * @author necmikilic
 *
 * The class represents lines of an order. Each line can have a coffee and multiple toppings
 * The total amount is sum of the toppings' amount 
 */
@Entity
@Table(name = "Order_Lines")
public class OrderLine {

    private Long id;
    private Order order;
    private Coffee coffee;
    private Set<Topping> toppings = new HashSet<>();
	private BigDecimal totalAmount;       
	
	@JsonBackReference
    @ManyToOne
    @JoinColumn(name="order_id", nullable=false)
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "coffee_id", referencedColumnName = "id")
	public Coffee getCoffee() {
		return coffee;
	}

	public void setCoffee(Coffee coffee) {
		this.coffee = coffee;
	}

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	public Set<Topping> getToppings() {
		return toppings;
	}

	public void setToppings(Set<Topping> toppings) {
		this.toppings = toppings;
	}

	@Column(name = "total_amount")
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	
}
