package com.coffeetime.coffeeshop.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coffeetime.coffeeshop.domain.Order;
import com.coffeetime.coffeeshop.repository.OrderRepository;

@Service
@Transactional
public class OrderService {
	
	Logger logger = LoggerFactory.getLogger(OrderService.class);
	
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderPricingService pricingService;
    
    @Autowired
    private ToppingService toppingService;

    public Order save(Order order) {
    	// Set order of the order lines and score toppings order counts
    	order.getOrderLines().forEach(orderLine -> {
    		orderLine.setOrder(order);
    		
    		orderLine.getToppings().forEach(topping -> {
    			topping.setOrderCount(topping.getOrderCount() + 1);
    			toppingService.save(topping);
    		});
    	});
    	
    	// Set order original and discount amounts
    	pricingService.setOrderAmounts(order);
    	order.setOrderDate(new Date());
        Order saved = orderRepository.save(order);
        logger.info("Order saved with Id: {}", saved.getId());
        return saved;
    }

    public Optional<Order> getOrder(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }   
    
    public void delete(Long orderId) {
        orderRepository.deleteById(orderId);
        logger.info("Order with Id {} deleted", orderId);
    }
    
    // For unit tests
    public void deleteAll() {
        orderRepository.deleteAll();
    }
}
