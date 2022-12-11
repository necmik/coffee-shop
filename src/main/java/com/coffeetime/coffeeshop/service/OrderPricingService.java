package com.coffeetime.coffeeshop.service;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.coffeetime.coffeeshop.domain.Order;
import com.coffeetime.coffeeshop.domain.OrderLine;
import com.coffeetime.coffeeshop.domain.Topping;

/**
 * 
 * @author necmikilic
 * Management of order pricing services with discounts
 */
@Service
public class OrderPricingService {

	Logger logger = LoggerFactory.getLogger(OrderPricingService.class);
	
	public void setOrderAmounts(Order order) {
		BigDecimal originalAmount = BigDecimal.ZERO; // Total amount of the order
    	BigDecimal lowestLineAmount = BigDecimal.valueOf(1000000000); // Lowest amount of the order lines
    	double discountPercentage = 0.25;
    	BigDecimal minimumAmountForDiscount = BigDecimal.valueOf(12);
    	
        for (OrderLine orderLine : order.getOrderLines()) {        	
        	BigDecimal orderLineAmount = BigDecimal.ZERO;
        	orderLineAmount = orderLineAmount.add(orderLine.getCoffee().getAmount());
        	for(Topping topping : orderLine.getToppings()) {
        		orderLineAmount = orderLineAmount.add(topping.getAmount());
        	};
        	orderLine.setTotalAmount(orderLineAmount);
        	
        	originalAmount = originalAmount.add(orderLineAmount);
        	lowestLineAmount = lowestLineAmount.min(orderLineAmount);            
        };
        
        order.setOriginalAmount(originalAmount);
        
        BigDecimal discountAmount = BigDecimal.ZERO;
        if (originalAmount.compareTo(minimumAmountForDiscount) > 0) {
        	discountAmount = originalAmount.multiply(BigDecimal.valueOf(discountPercentage));
        }
        
        if (order.getOrderLines().size() >= 3) {
        	discountAmount = discountAmount.min(lowestLineAmount);
        }
        	
        order.setDiscountedAmount(originalAmount.subtract(discountAmount)); 
        
        if (originalAmount.compareTo(discountAmount) != 0) {
        	logger.info("Discount applied. New price is {}", order.getDiscountedAmount());
        }
	}
}
