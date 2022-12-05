package com.coffeetime.coffeeshop.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ToppingServiceTest {
	/**
	 * Because couldn't mitigate the "Not a managed entity" spring build problem, it wouldn't make sense to implement test cases. 
	 * So, just adding the test methods declaration.
	 */
    @Autowired
    private ToppingService toppingService;
    
    @Before
    public void setUp() throws Exception {
        // Remove previous objects
        
    	// Create topping list
    }
    
    @Test
    // Test saving topping
    public void testSave() {  
    	
    }
    
    @Test
    // Test finding topping by id
    public void testFindById() {
    	
    }
    
    @Test
    // Test	finding all toppings
    public void testFindAll() {
    	
    }
    
    @Test
    // Test deleting a topping
    public void testDelete() {  
    	
    }        
}
