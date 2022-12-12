package com.coffeetime.coffeeshop.service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Set;

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
		
		Set<OrderLine> orderLines = order.getOrderLines();
		
		// Set amount of the order lines
		orderLines.forEach(orderLine -> {   
        	BigDecimal orderLineAmount = orderLine.getToppings().stream()
                    .map(x -> x.getAmount())    
                    .reduce(orderLine.getCoffee().getAmount(), BigDecimal::add); 
            
        	orderLine.setTotalAmount(orderLineAmount);
        });
        
        // Set total amount of the order
        BigDecimal originalAmount = orderLines
        		.stream()
        		.map(x -> x.getTotalAmount())
        		.reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setOriginalAmount(originalAmount);
        
        BigDecimal discountedAmount = getDiscountedAmount(originalAmount, orderLines);
        order.setDiscountedAmount(discountedAmount); 
        
        if (originalAmount.compareTo(discountedAmount) != 0) {
        	logger.info("Discount applied. New total amount is {}", order.getDiscountedAmount());
        }        
	}
	
	/**
	 * 
	 * @param originalAmount : Total amount of the order
	 * @param orderLines : Order lines
	 * @return 
	 * 
	 * The method returns discounted amount of the order if applied. Otherwise returns the original amount.
	 * Discount amount is decided by total amount of the order and cheapest order line (coffee).
	 * 1) If the total amount is higher than minimumAmountForDiscount then %25 discount is applied.
	 * 2) If there are 3 or more coffees in order, then the discount amount will be the cheapest one.
	 * 3) If both conditions are eligible, then the lower discount is applied.
	 */
    private BigDecimal getDiscountedAmount(BigDecimal originalAmount, Set<OrderLine> orderLines) {
    	BigDecimal discountAmount = BigDecimal.ZERO;
        
        if (originalAmount.compareTo(minimumAmountForDiscount) > 0) {
        	discountAmount = originalAmount.multiply(BigDecimal.valueOf(discountPercentage));
        }
        
        OrderLine cheapestOrderLine = orderLines
        		.stream()
        		.min(Comparator.comparing(OrderLine::getTotalAmount))
        		.get();
        
        if (orderLines.size() >= 3) {
        	discountAmount = discountAmount.min(cheapestOrderLine.getTotalAmount());
        }
        
        return originalAmount.subtract(discountAmount); 
    }
}
