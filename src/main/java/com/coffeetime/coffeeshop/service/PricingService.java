package com.coffeetime.coffeeshop.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.coffeetime.coffeeshop.domain.Order;

@Service
public class PricingService {

	public BigDecimal applyDiscount(Order order) {
		BigDecimal discountedAmount = order.getOriginalAmount();
		
		return discountedAmount;
	}
}
