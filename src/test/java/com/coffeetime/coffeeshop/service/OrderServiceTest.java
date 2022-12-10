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
    // Validate saving order
    public void testSave() {  
    	
    }
    
    @Test
    // Validate finding order by id
    public void testFindById() {
    	
    }
    
    @Test
    // Validate finding orders by date
    public void testFindByDate() {
    	
    }
    
    @Test
    // Validate	finding all orders
    public void testFindAll() {
    	
    }
    
    @Test
    // Verify amount of the order is correct
    public void testOrderAmount() {
    	
    }
    
    @Test
    // Verify that %25 discount applied
    public void testDiscountApplied() {
    	
    }
    
    @Test
    // Verify that coffee with lowest amount becomes free for orders with more than 3 coffees
    public void testFreeCoffeeApplied() {
    	
    }
    
    @Test
    // Validate	discount is not applied to order
    public void testDiscountNotApplied() {
    	
    }
    
    @Test
    // Validate	order count of topping increased after a new order
    public void testToppingOrderCount() {
    	
    }
    
    @Test
    // Verify that exception is thrown for invalid coffee
    public void testInvalidCoffeeId_Negative() {
    	
    }
    
    @Test
    // Verify that exception is thrown for invalid topping
    public void testInvalidToppingId_Negative() {
    	
    }
    
    @Test
    // Validate deleting order with its order lines
    public void testDelete() {  
    	
    }
}
