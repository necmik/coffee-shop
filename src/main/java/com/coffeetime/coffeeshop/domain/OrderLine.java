package com.coffeetime.coffeeshop.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class OrderLine {

	@Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Coffee coffee;
    
    /**
     * Each coffee order may have multiple toppings
     */
    @OneToMany(mappedBy = "orderLine", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Topping> toppings = new ArrayList<>();
    
	@Column(name = "total_amount")
	private BigDecimal totalAmount;
    
    @ManyToOne
    private Order order;
}
