package com.coffeetime.coffeeshop.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coffeetime.coffeeshop.domain.Coffee;
import com.coffeetime.coffeeshop.domain.Order;
import com.coffeetime.coffeeshop.domain.OrderLine;
import com.coffeetime.coffeeshop.domain.Topping;
import com.coffeetime.coffeeshop.exception.HttpEmptyOrderException;
import com.coffeetime.coffeeshop.exception.HttpNotFoundErrorException;
import com.coffeetime.coffeeshop.exception.HttpProductNotFoundException;
import com.coffeetime.coffeeshop.payload.OrderDto;
import com.coffeetime.coffeeshop.payload.OrderLineDto;
import com.coffeetime.coffeeshop.service.CoffeeService;
import com.coffeetime.coffeeshop.service.OrderService;
import com.coffeetime.coffeeshop.service.ToppingService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CoffeeService coffeeService;
	
	@Autowired
	private ToppingService toppingService;
	
	@PostMapping
	public ResponseEntity<Order> saveOrder(@RequestBody OrderDto orderDto) throws URISyntaxException {
		Set<OrderLineDto> orderLines = orderDto.getOrderLines();

        if (orderLines.size() == 0) {
            throw new HttpEmptyOrderException("At least one coffee must be added to the Order");
        }
        
        Order order = convertToOrder(orderDto);        
        Order result = orderService.save(order);

        return ResponseEntity.created(new URI("/api/orders/" + result.getId())).body(result);
    }
	
	@GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
	
    @DeleteMapping("/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable Long id) {
        if (orderService.getOrder(id) == null) {
            throw new HttpNotFoundErrorException("Cannot delete because the order with id " + id + " does not exist");
        }
        orderService.delete(id);
        return ResponseEntity.ok().build();
    }
    
    private Order convertToOrder(OrderDto orderDto) {
    	Order order = new Order();
    	orderDto.getOrderLines().forEach(orderLineDto -> {
        	Optional<Coffee> optCoffee = coffeeService.getCoffee(orderLineDto.getCoffeeId());
        	// Throw exception if coffee with the id does not exist
        	if (optCoffee.isEmpty()) {
                throw new HttpProductNotFoundException(String.format("Coffe with id %l not found", orderLineDto.getCoffeeId()));
        	}
        	
        	OrderLine orderLine = new OrderLine();
        	orderLine.setCoffee(optCoffee.get());
        	
        	if (orderLineDto.getToppingIds() != null) {
        		orderLineDto.getToppingIds().forEach(toppingId -> {
            		Optional<Topping> optTopping = toppingService.getTopping(toppingId);
            		if (optTopping.isEmpty()) {
                        throw new HttpProductNotFoundException(String.format("Topping with id %l not found", toppingId));
                	}
            		Topping topping = optTopping.get();
            		orderLine.getToppings().add(topping);
            	});
            	
        		order.getOrderLines().add(orderLine);
        	}    
    	});
       
        return order;
    }
}
