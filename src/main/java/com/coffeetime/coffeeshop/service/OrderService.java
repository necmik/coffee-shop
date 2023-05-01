package com.coffeetime.coffeeshop.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coffeetime.coffeeshop.domain.Coffee;
import com.coffeetime.coffeeshop.domain.Order;
import com.coffeetime.coffeeshop.domain.OrderLine;
import com.coffeetime.coffeeshop.domain.Topping;
import com.coffeetime.coffeeshop.exception.HttpEmptyOrderException;
import com.coffeetime.coffeeshop.exception.HttpNotFoundErrorException;
import com.coffeetime.coffeeshop.exception.HttpProductNotFoundException;
import com.coffeetime.coffeeshop.payload.OrderDto;
import com.coffeetime.coffeeshop.repository.OrderRepository;

@Service
@Transactional
public class OrderService {
	
	Logger logger = LoggerFactory.getLogger(OrderService.class);
	
    private OrderRepository orderRepository;
    private OrderPricingService pricingService;
    private CoffeeService coffeeService;
    private ToppingService toppingService;
    
	@Autowired
	public OrderService(OrderRepository orderRepository,
			OrderPricingService pricingService,
			CoffeeService coffeeService,
			ToppingService toppingService) {
		this.orderRepository = orderRepository;
		this.pricingService = pricingService;
		this.coffeeService = coffeeService;
		this.toppingService = toppingService;
	}

    public Order save(OrderDto orderDto) {
        if (orderDto.getOrderLines().size() == 0) {
            throw new HttpEmptyOrderException("At least one coffee must be added to the Order");
        }
        
    	Order order = convertToOrder(orderDto);
    	
    	// Set original and discounted amounts
    	pricingService.setOrderAmounts(order);
    	
    	// Save order
        Order saved = orderRepository.save(order);
    	
        // Promote topping order counts
    	order.getOrderLines().forEach(orderLine -> {
    		orderLine.getToppings().forEach(topping -> {
    			topping.setOrderCount(topping.getOrderCount() + 1);
    			toppingService.save(topping);
    		});
    	});
    	
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
    	 if (getOrder(orderId) == null) {
             throw new HttpNotFoundErrorException("Cannot delete because the order with id " + orderId + " does not exist");
         }
        orderRepository.deleteById(orderId);
        logger.info("Order with Id {} deleted", orderId);
    }
    
    // For unit tests
    public void deleteAll() {
        orderRepository.deleteAll();
    }
    
    private Order convertToOrder(OrderDto orderDto) {
    	Order order = new Order();
    	orderDto.getOrderLines().forEach(orderLineDto -> {
        	Optional<Coffee> optCoffee = coffeeService.getCoffee(orderLineDto.getCoffeeId());
        	// Throw exception if coffee with the id does not exist
        	if (optCoffee.isEmpty()) {
                throw new HttpProductNotFoundException(String.format("Coffe with id %d not found", orderLineDto.getCoffeeId()));
        	}
        	
        	OrderLine orderLine = new OrderLine();
        	orderLine.setCoffee(optCoffee.get());
        	
        	if (orderLineDto.getToppingIds() != null) {
        		orderLineDto.getToppingIds().forEach(toppingId -> {
            		Optional<Topping> optTopping = toppingService.getTopping(toppingId);
            		if (optTopping.isEmpty()) {
                        throw new HttpProductNotFoundException(String.format("Topping with id %d not found", toppingId));
                	}
            		Topping topping = optTopping.get();
            		orderLine.getToppings().add(topping);
            	});
            	
        		order.getOrderLines().add(orderLine);
        		orderLine.setOrder(order);
        	}    
    	});
       
        return order;
    }
}
