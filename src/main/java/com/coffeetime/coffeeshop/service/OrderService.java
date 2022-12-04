package com.coffeetime.coffeeshop.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.coffeetime.coffeeshop.domain.Order;
import com.coffeetime.coffeeshop.domain.OrderLine;
import com.coffeetime.coffeeshop.repository.OrderRepository;

public class OrderService {
	
	Logger logger = LoggerFactory.getLogger(OrderService.class);
	
    @Autowired
    private OrderRepository orderRepository;

    public Order save(Order order) {
    	setPrice(order);
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
    
    private void setPrice(Order order) {       
    	
    	BigDecimal originalAmount = BigDecimal.ZERO; // Total amount of the order
    	BigDecimal lowestLineAmount = BigDecimal.valueOf(1000000000); // Lowest amount of the coffee with toppings
    	
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
        if (originalAmount.compareTo(BigDecimal.valueOf(12)) > 0) {
        	discountAmount = originalAmount.multiply(BigDecimal.valueOf(0.25));
        }
        
        if (order.getOrderLines().size() >= 3) {
        	discountAmount = discountAmount.min(lowestLineAmount);
        }
        	
        order.setDiscountedAmount(originalAmount.subtract(discountAmount)); 
    }
}
