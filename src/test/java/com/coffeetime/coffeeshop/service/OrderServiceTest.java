package com.coffeetime.coffeeshop.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {
	
	/**
	 * Because couldn't mitigate the "Not a managed entity" spring build problem, it wouldn't make sense to implement test cases. 
	 * So, just adding the test methods declaration.
	 */
    @Autowired
    private OrderService orderService;
    
    @Before
    public void setUp() throws Exception {
        // Remove previous objects
        
    	// Create test orders
    }
    
    @Test
    // Test saving order
    public void testSave() {  
    	
    }
    
    @Test
    // Test finding order by id
    public void testFindById() {
    	
    }
    
    @Test
    // Test finding orders by coffee
    public void testFindByCoffee() {

    }
    
    @Test
    // Test finding orders by date
    public void testFindByDate() {
    	
    }
    
    @Test
    // Test	finding all orders
    public void testFindAll() {
    	
    }
    
    @Test
    // Test	applying discount to order
    public void testDiscountApplied() {
    	
    }
    
    @Test
    // Test	not applying discount to order
    public void testDiscountNotApplied() {
    	
    }
    
    @Test
    // Test	order count of topping increased after a new order
    public void testToppingOrderCount() {
    	
    }
}
