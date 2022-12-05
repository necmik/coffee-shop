package com.coffeetime.coffeeshop.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoffeeServiceTest {

	/**
	 * Because couldn't mitigate the "Not a managed entity" spring build problem, it wouldn't make sense to implement test cases. 
	 * So, just adding the test methods declaration.
	 */
    @Autowired
    private CoffeeService coffeeService;
    
    @Before
    public void setUp() throws Exception {
        // Remove previous objects
        
    	// Create coffee list
    }
    
    @Test
    // Test saving coffee
    public void testSave() {  
    	
    }
    
    @Test
    // Test finding coffee by id
    public void testFindById() {
    	
    }
    
    @Test
    // Test	finding all coffees
    public void testFindAll() {
    	
    }
    
    @Test
    // Test deleting a coffee
    public void testDelete() {  
    	
    }        
}
