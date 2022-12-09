package com.coffeetime.coffeeshop.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coffeetime.coffeeshop.domain.Order;
import com.coffeetime.coffeeshop.domain.OrderLine;
import com.coffeetime.coffeeshop.repository.OrderRepository;

@Service
@Transactional
public class OrderService {
	
	Logger logger = LoggerFactory.getLogger(OrderService.class);
	
    @Autowired
    private OrderRepository orderRepository;

    public Order save(Order order) {
    	setOrderAmounts(order);
        Order saved = orderRepository.save(order);
        logger.info("Order saved with Id: {}", saved.getId());
        return saved;
    }

    public void delete(Long orderId) {
        orderRepository.deleteById(orderId);
        logger.info("Order with Id {} deleted", orderId);
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }   
    
    private void setOrderAmounts(Order order) {       
    	
    	BigDecimal originalAmount = BigDecimal.ZERO; // Total amount of the order
    	BigDecimal lowestLineAmount = BigDecimal.valueOf(1000000000); // Lowest amount of the coffee with toppings
    	double discountPercentage = 0.25;
    	BigDecimal minimumAmountForDiscount = BigDecimal.valueOf(12);
    	
        for (OrderLine orderLine : order.getOrderLines()) {        	
        	BigDecimal orderLineAmount = BigDecimal.ZERO;
        	orderLine.getToppings().forEach(topping -> {
        		orderLineAmount.add(topping.getAmount());
        	});
        	orderLine.setTotalAmount(orderLineAmount);
        	
        	originalAmount.add(orderLineAmount);
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
        	logger.info("Discount applied. New price is {}", discountAmount);
        }
    }
}
