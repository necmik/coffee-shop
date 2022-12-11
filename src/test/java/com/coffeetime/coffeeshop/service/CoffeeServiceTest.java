package com.coffeetime.coffeeshop.service;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import com.coffeetime.coffeeshop.domain.Coffee;

import jakarta.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoffeeServiceTest {

    @Autowired
    private CoffeeService coffeeService;
    
    private Coffee[] testCoffees;
    final static int TEST_NumberOfCoffees = 6;
    
    @Before
    public void setUp() throws Exception {  
    	// Remove previous objects
    	deleteAllObjects();
        
    	// Create coffee list to use during the tests
    	// Valid record
    	testCoffees = new Coffee[TEST_NumberOfCoffees];
    	
    	Coffee coffee0 = new Coffee();
    	coffee0.setName("Americano");
    	coffee0.setAmount(BigDecimal.valueOf(4.5));
    	testCoffees[0] = coffee0;
    	
    	// Record with invalid amount
    	Coffee coffee1 = new Coffee();
    	coffee1.setName("Latte");
    	coffee1.setAmount(BigDecimal.valueOf(0.5));
    	testCoffees[1] = coffee1;
    	
    	// Record same name with the Record 0
    	Coffee coffee2 = new Coffee();
    	coffee2.setName("Americano");
    	coffee2.setAmount(BigDecimal.valueOf(6));
    	testCoffees[2] = coffee2;  
    	
    	// Record with invalid name
    	Coffee coffee3 = new Coffee();
    	coffee3.setAmount(BigDecimal.valueOf(6));
    	testCoffees[3] = coffee3;  
    	
    	// Record with invalid name
    	Coffee coffee4 = new Coffee();
    	coffee4.setName("");
    	coffee4.setAmount(BigDecimal.valueOf(4));
    	testCoffees[4] = coffee4;  
    	
    	// Valid record
    	Coffee coffee5 = new Coffee();
    	coffee5.setName("Macchiato");
    	coffee5.setAmount(BigDecimal.valueOf(3));
    	testCoffees[5] = coffee5;
    }
    
    @After
    public void tearDown() throws Exception {
    	deleteAllObjects() ;
    }   
    
    // Verify happy path for saving a coffee
    @Test   
    public void testSave() {  
    	Coffee coffee = coffeeService.save(testCoffees[0]);
    	Optional<Coffee> savedCoffee = coffeeService.getCoffee(coffee.getId());
    	if (savedCoffee.isEmpty() || !compareCoffee(testCoffees[0], savedCoffee.get())) {
            fail("Not storing or retrieving Coffee");
        }
    	
    }
    
    // Verify that saving a coffee with amount lower than 1 throws error
	@Test(expected = ConstraintViolationException.class)    
    public void testSaveWithInvalidAmount_Negative() {  
		coffeeService.save(testCoffees[1]);
    }

	// Verify that coffee name must be unique
	@Test(expected = DataIntegrityViolationException.class)    
    public void testSaveDuplicatedName_Negative() {  
    	coffeeService.save(testCoffees[0]);
    	coffeeService.save(testCoffees[2]);
    }
    
	// Verify that coffee name must be defined
    @Test(expected = ConstraintViolationException.class)    
    public void testSaveNullName_Negative() {  
    	coffeeService.save(testCoffees[3]);
    }
    
    // Verify that coffee name is not empty
    @Test(expected = ConstraintViolationException.class)    
    public void testSaveEmptyName_Negative() {  
    	coffeeService.save(testCoffees[4]);
    }
    
    // Test finding coffee by id
    @Test    
    public void testFindById() {
    	Coffee coffee = coffeeService.save(testCoffees[0]);
    	Optional<Coffee> savedCoffee = coffeeService.getCoffee(coffee.getId());
    	assertTrue(savedCoffee.isPresent());
    }
    
    // Test	finding all coffees
    @Test    
    public void testFindAll() {
    	coffeeService.save(testCoffees[0]);
    	coffeeService.save(testCoffees[5]);
    	List<Coffee> coffees = coffeeService.getAllCoffees();
    	assertEquals(2, coffees.size());
    }
    
    // Verify happy path for updating a coffee
    @Test    
    public void testUpdate() {  
    	Coffee coffee = coffeeService.save(testCoffees[0]);
    	coffee.setAmount(BigDecimal.valueOf(5));
    	coffeeService.save(coffee);
    	Optional<Coffee> savedCoffee = coffeeService.getCoffee(coffee.getId());
    	assertTrue(savedCoffee.isPresent() && savedCoffee.get().getAmount().compareTo(coffee.getAmount()) == 0);
    }
    
    // Test deleting a coffee
    @Test    
    public void testDelete() {  
    	Coffee coffee = coffeeService.save(testCoffees[0]);
    	Optional<Coffee> savedCoffee = coffeeService.getCoffee(coffee.getId());
    	assertTrue(savedCoffee.isPresent());
    	coffeeService.delete(savedCoffee.get().getId());
    	savedCoffee = coffeeService.getCoffee(coffee.getId());
    	assertFalse(savedCoffee.isPresent());
    }    
    
    private void deleteAllObjects() {
    	coffeeService.deleteAll();
    }
    
    private boolean compareCoffee(Coffee a, Coffee b) {
        if ((a == null) || (b == null)) {
            return false;
        }

        return ((a.getName().equals(b.getName()) &&
        		a.getAmount().compareTo(b.getAmount()) == 0));
    }
}
