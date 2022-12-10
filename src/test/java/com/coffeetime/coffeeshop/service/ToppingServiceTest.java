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
    // Verify happy path for saving a topping
    public void testSave() {  
    	
    }
    
    @Test
    // Verify that saving a coffee with amount lower than 1 throws error
    public void testSaveWithZeroAmount_Negative() {  
    	
    }
    
    @Test
    // Verify that topping name must be unique
    public void testSaveDuplicatedName_Negative() {  
    	
    }
    
    @Test
    // Verify that topping name must be defined
    public void testSaveNullName_Negative() {  
    	
    }
    
    @Test
    // Verify that topping name is not empty
    public void testSaveEmptyName_Negative() {  
    	
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
    // Verify happy path for updating a topping
    public void testUpdate() {  
    	
    }
    
    @Test
    // Test deleting a topping
    public void testDelete() {  
    	
    }    
    
    @Test
    // Validate order count is set to zero by default.
    public void testOrderCountSetZero() {  
    	
    }    
    
    @Test
    // Test top toppings
    public void testTopNToppings() {  
    	
    }    
}
