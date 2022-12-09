package com.coffeetime.coffeeshop.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
import lombok.Data;

/**
 * 
 * @author necmikilic
 *
 * The class represents lines of an order. Each line can have a coffee and multiple toppings
 * The total amount is sum of the toppings' amount 
 */
@Entity
@Data
public class OrderLine {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coffee_id", referencedColumnName = "id")
    private Coffee coffee;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Topping> toppings = new ArrayList<>();
    
	@Column(name = "total_amount")
	private BigDecimal totalAmount;
    
    @ManyToOne
    private Order order;
}
