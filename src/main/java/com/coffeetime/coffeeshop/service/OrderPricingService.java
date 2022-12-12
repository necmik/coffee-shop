package com.coffeetime.coffeeshop.service;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.coffeetime.coffeeshop.domain.Order;
import com.coffeetime.coffeeshop.domain.OrderLine;

/**
 * 
 * @author necmikilic
 * Management of order pricing services with discounts
 */
@Service
public class OrderPricingService {

	Logger logger = LoggerFactory.getLogger(OrderPricingService.class);
	
	private static final double discountPercentage = 0.25;
	private static final BigDecimal minimumAmountForDiscount = BigDecimal.valueOf(12);
	
	public void setOrderAmounts(Order order) {
		BigDecimal originalAmount = BigDecimal.ZERO; // Total amount of the order
    	BigDecimal lowestLineAmount = BigDecimal.valueOf(1000000000); // Lowest amount of the order lines
    	
        for (OrderLine orderLine : order.getOrderLines()) {   
        	// Calculate amount of the order line (coffee + toppings)
        	BigDecimal orderLineAmount = orderLine.getToppings().stream()
                    .map(x -> x.getAmount())    
                    .reduce(orderLine.getCoffee().getAmount(), BigDecimal::add); 
            
        	orderLine.setTotalAmount(orderLineAmount);
        	
        	originalAmount = originalAmount.add(orderLineAmount);
        	lowestLineAmount = lowestLineAmount.min(orderLineAmount);            
        };
        
        order.setOriginalAmount(originalAmount);
        
        BigDecimal discountAmount = BigDecimal.ZERO;
        
        // If the total amount is higher than minimumAmountForDiscount then discount amount is %25
        if (originalAmount.compareTo(minimumAmountForDiscount) > 0) {
        	discountAmount = originalAmount.multiply(BigDecimal.valueOf(discountPercentage));
        }
        
        // If there are 3 or more coffees in order, then the cheapest one is free
        if (order.getOrderLines().size() >= 3) {
        	discountAmount = discountAmount.min(lowestLineAmount);
        }
        
        // Select the minimum discount to apply to the order
        order.setDiscountedAmount(originalAmount.subtract(discountAmount)); 
        
        if (originalAmount.compareTo(discountAmount) != 0) {
        	logger.info("Discount applied. New price is {}", order.getDiscountedAmount());
        }
	}
}
