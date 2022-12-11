package com.coffeetime.coffeeshop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.coffeetime.coffeeshop.domain.Coffee;
import com.coffeetime.coffeeshop.domain.Order;
import com.coffeetime.coffeeshop.domain.OrderLine;
import com.coffeetime.coffeeshop.domain.Topping;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {
	
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private CoffeeService coffeeService;
    
    @Autowired
    private ToppingService toppingService;
    
    private Coffee[] testCoffees;
    final static int TEST_NumberOfCoffees = 2;
    
    private Topping[] testToppings;
    final static int TEST_NumberOfToppings = 3;
    
    private Order[] testOrders;
    final static int TEST_NumberOfOrders = 4;
    
    @Before
    public void setUp() throws Exception {
    	// Remove previous objects
    	deleteAllObjects();
        
    	// Create coffees and toppings to be used for orders
    	createCoffees();
    	createToppings();
    	
    	// Create test orders
    	testOrders = new Order[TEST_NumberOfOrders];
    	    	
    	OrderLine orderLine0 = new OrderLine();
    	orderLine0.setCoffee(testCoffees[0]);
    	orderLine0.setToppings(new HashSet<>(Arrays.asList(testToppings[0])));
    	
    	OrderLine orderLine1 = new OrderLine();
    	orderLine1.setCoffee(testCoffees[1]);
    	orderLine1.setToppings(new HashSet<>(Arrays.asList(testToppings[1])));
    	
    	OrderLine orderLine2 = new OrderLine();
    	orderLine2.setCoffee(testCoffees[0]);
    	orderLine2.setToppings(new HashSet<>(Arrays.asList(testToppings[0], testToppings[1])));
    	
    	// Valid record with one order line(coffee)
    	Order order0 = new Order();
    	order0.setOrderLines(new HashSet<>(Arrays.asList(orderLine0)));
    	testOrders[0] = order0;
    	
    	// Valid record with one order line(coffee)
    	Order order1 = new Order();
    	order1.setOrderLines(new HashSet<>(Arrays.asList(orderLine1)));
    	testOrders[1] = order1;
    	
    	// Valid record with 2 order lines(coffees)
    	Order order2 = new Order();
    	order2.setOrderLines(new HashSet<>(Arrays.asList(orderLine0, orderLine2)));
    	testOrders[2] = order2;
    	
    	// Valid record with 3 order lines(coffees)
    	Order order3 = new Order();
    	order3.setOrderLines(new HashSet<>(Arrays.asList(orderLine0, orderLine1, orderLine2)));
    	testOrders[3] = order3;
    }
    
    @After
    public void tearDown() throws Exception {
    	deleteAllObjects() ;
    }  
    
    @Test
    // Validate saving order
    public void testSave() {  
    	Order order = orderService.save(testOrders[0]);
    	Optional<Order> savedOrder = orderService.getOrder(order.getId());
    	assertTrue(savedOrder.isPresent());
    	
    	// Validate amount. 
    	// The original amount must be 4.5(coffee) + 2(topping) = 6.5
    	assertTrue(savedOrder.get().getOriginalAmount().compareTo(BigDecimal.valueOf(6.5)) == 0);
    }
    
    @Test
    // Validate	finding all orders
    public void testFindAll() {
    	Order order0 = orderService.save(testOrders[0]);
    	Order order1 = orderService.save(testOrders[1]);
    	orderService.save(order0);
    	orderService.save(order1);
    	
    	List<Order> orders = orderService.getAllOrders();
    	assertEquals(2, orders.size());
    }
    
    @Test
    // Verify that %25 discount applied
    public void testDiscountApplied() {
    	Order order = orderService.save(testOrders[2]);
    	Order savedOrder = orderService.save(order);
    	
    	BigDecimal originalAmount = getOriginalAmount(order);
        BigDecimal discountedAmount = originalAmount.multiply(BigDecimal.valueOf(0.75));
         
    	// Validate original and discount amounts 
    	assertTrue(savedOrder.getOriginalAmount().compareTo(originalAmount) == 0);
    	assertTrue(savedOrder.getDiscountedAmount().compareTo(discountedAmount) == 0);
    }
    
    @Test
    // Verify that coffee with lowest amount becomes free for orders with more than 3 coffees
    public void testFreeCoffeeApplied() {
    	Order order = orderService.save(testOrders[3]);
    	Order savedOrder = orderService.save(order);
    	
    	BigDecimal originalAmount = getOriginalAmount(order);
    	// 2nd coffee with 3+1.5 amount must be set free
        BigDecimal discountedAmount = originalAmount.subtract(BigDecimal.valueOf(4.5));
         
    	// Validate original and discount amounts 
    	assertTrue(savedOrder.getOriginalAmount().compareTo(originalAmount) == 0);
    	assertTrue(savedOrder.getDiscountedAmount().compareTo(discountedAmount) == 0);
    }
    
    @Test
    // Validate	order count of topping increased after a new order
    public void testToppingOrderCount() {
    	Order order = orderService.save(testOrders[0]);
    	Optional<Order> savedOrder = orderService.getOrder(order.getId());
    	assertTrue(savedOrder.isPresent());
    	
    	Optional<Topping> topping = toppingService.getTopping(testToppings[0].getId());
    	assertTrue(topping.isPresent()); 
    	assertEquals(1, topping.get().getOrderCount());
    }
    
    @Test
    // Validate deleting order with its order lines
    public void testDelete() {  
    	Order order = orderService.save(testOrders[0]);
    	Optional<Order> savedOrder = orderService.getOrder(order.getId());
    	assertTrue(savedOrder.isPresent());
    	
    	orderService.delete(savedOrder.get().getId());
    	savedOrder = orderService.getOrder(order.getId());
    	assertFalse(savedOrder.isPresent());
    }
    
    @Test
    // Validate list of top toppings
    public void testTopNToppings() {  
    	// Create orders to promote toppings    	
    	orderService.save(testOrders[0]);
    	orderService.save(testOrders[1]);
    	
    	// Toppings indexed with 0 and 1 must be returned from the top 2 list
    	List<Topping> topToppings = toppingService.getTopN(2);
    	assertThat(topToppings)
	        .hasSize(2)
	        .extracting(Topping::getName)
	        .containsExactlyInAnyOrder("Milk", "Cacao"); 
    }
    
    private void deleteAllObjects() {
    	orderService.deleteAll();
    	
    	// Delete coffee objects created from data.sql
    	coffeeService.deleteAll();
    	
    	// Delete topping objects created from data.sql
    	toppingService.deleteAll();
    }
    
    private void createCoffees() {
    	testCoffees = new Coffee[TEST_NumberOfCoffees];
    	
    	Coffee coffee0 = new Coffee();
    	coffee0.setName("Americano");
    	coffee0.setAmount(BigDecimal.valueOf(4.5));
    	Coffee savedCoffee = coffeeService.save(coffee0);
    	testCoffees[0] = savedCoffee;
    	
    	Coffee coffee1 = new Coffee();
    	coffee1.setName("Macchiato");
    	coffee1.setAmount(BigDecimal.valueOf(3));
    	savedCoffee = coffeeService.save(coffee1);
    	testCoffees[1] = savedCoffee;
    }
    
    private void createToppings() {
    	testToppings = new Topping[TEST_NumberOfToppings];
    	
    	Topping topping0 = new Topping();
    	topping0.setName("Milk");
    	topping0.setAmount(BigDecimal.valueOf(2));
    	Topping savedTopping = toppingService.save(topping0);
    	testToppings[0] = savedTopping;    	
    	
    	Topping topping1 = new Topping();
    	topping1.setName("Cacao");
    	topping1.setAmount(BigDecimal.valueOf(1.5));    	
    	savedTopping = toppingService.save(topping1);
    	testToppings[1] = savedTopping;
    	
    	Topping topping2 = new Topping();
    	topping2.setName("Sugar");
    	topping2.setAmount(BigDecimal.valueOf(1.2));    	
    	savedTopping = toppingService.save(topping2);
    	testToppings[2] = savedTopping;
    	
    }
    
    private BigDecimal getOriginalAmount(Order order) {
    	BigDecimal originalAmount = BigDecimal.ZERO;
    	for (OrderLine orderLine : order.getOrderLines()) {        	
         	BigDecimal orderLineAmount = BigDecimal.ZERO;
         	orderLineAmount = orderLineAmount.add(orderLine.getCoffee().getAmount());
         	for(Topping topping : orderLine.getToppings()) {
         		orderLineAmount = orderLineAmount.add(topping.getAmount());
         	};
         	orderLine.setTotalAmount(orderLineAmount);
         	
         	originalAmount = originalAmount.add(orderLineAmount);
         };
         
         return originalAmount;
    }
}
