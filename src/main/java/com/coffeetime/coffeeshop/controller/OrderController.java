package com.coffeetime.coffeeshop.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coffeetime.coffeeshop.domain.Order;
import com.coffeetime.coffeeshop.domain.OrderLine;
import com.coffeetime.coffeeshop.exception.HttpEmptyOrderException;
import com.coffeetime.coffeeshop.service.OrderService;
import com.coffeetime.coffeeshop.service.ToppingService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ToppingService toppingService;
	
	@PostMapping("/add")
	public ResponseEntity<Order> saveOrder(@Valid Order order) throws URISyntaxException {
		List<OrderLine> orderLines = order.getOrderLines();

        if (orderLines.size() == 0) {
            throw new HttpEmptyOrderException("At least one coffee must be added to the Order");
        }
        
        order.getOrderLines().forEach(orderLine -> {
        	// Increase order count of the topping by 1
        	toppingService.increaseOrderCounts(orderLine.getToppings());
        	orderLine.setOrder(order);
        });
        
        order.setOrderDate(new Date());
        Order result = orderService.save(order);

        return ResponseEntity.created(new URI("/api/orders/" + result.getId())).body(result);
    }
}
