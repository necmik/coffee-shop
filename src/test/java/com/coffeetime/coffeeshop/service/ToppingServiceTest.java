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

import com.coffeetime.coffeeshop.domain.Topping;

import jakarta.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ToppingServiceTest {

    @Autowired
    private ToppingService toppingService;
    
    private Topping[] testToppings;
    final static int TEST_NumberOfToppings = 6;
    
    @Before
    public void setUp() throws Exception {
    	// Remove previous objects
    	deleteAllObjects();
        
    	// Create topping list to use during the tests    	
    	testToppings = new Topping[TEST_NumberOfToppings];
    	
    	// Valid record
    	Topping topping0 = new Topping();
    	topping0.setName("Milk");
    	topping0.setAmount(BigDecimal.valueOf(2));
    	testToppings[0] = topping0;
    	
    	// Invalid amount
    	Topping topping1 = new Topping();
    	topping1.setName("Water");
    	topping1.setAmount(BigDecimal.valueOf(0.5));
    	testToppings[1] = topping1;
    	
    	// Duplicated name 
    	Topping topping2 = new Topping();
    	topping2.setName("Milk");
    	topping2.setAmount(BigDecimal.valueOf(3));
    	testToppings[2] = topping2;
    	
    	// Invalid null name 
    	Topping topping3 = new Topping();
    	topping3.setAmount(BigDecimal.valueOf(2.5));
    	testToppings[3] = topping3;
    	
    	// Invalid empty name 
    	Topping topping4 = new Topping();
    	topping4.setName("");
    	topping4.setAmount(BigDecimal.valueOf(1.5));
    	testToppings[4] = topping4;
    	
    	// Valid record
    	Topping topping5 = new Topping();
    	topping5.setName("Cacao");
    	topping5.setAmount(BigDecimal.valueOf(1.5));
    	testToppings[5] = topping5;
    }
    
    @After
    public void tearDown() throws Exception {
    	deleteAllObjects() ;
    }   
    
    // Verify happy path for saving a topping
    @Test    
    public void testSave() {  
    	Topping topping = toppingService.save(testToppings[0]);
    	Optional<Topping> savedTopping = toppingService.getTopping(topping.getId());
    	if (savedTopping.isEmpty() || !compareCoffee(testToppings[0], savedTopping.get())) {
            fail("Not storing or retrieving Coffee");
        }
    }
    
    // Verify that saving a topping with amount lower than 1 throws error
	@Test(expected = ConstraintViolationException.class)    
    public void testSaveWithInvalidAmount_Negative() {  
		toppingService.save(testToppings[1]);
    }
    
	// Verify that topping name must be unique
	@Test(expected = DataIntegrityViolationException.class)      
    public void testSaveDuplicatedName_Negative() {  
    	toppingService.save(testToppings[0]);
    	toppingService.save(testToppings[2]);
    }
    
	// Verify that topping name must be defined
	@Test(expected = ConstraintViolationException.class)      
    public void testSaveNullName_Negative() {  
    	toppingService.save(testToppings[3]);
    }
    
    // Verify that topping name is not empty
	@Test(expected = ConstraintViolationException.class)       
    public void testSaveEmptyName_Negative() {  
    	toppingService.save(testToppings[4]);
    }
    
	// Test finding topping by id
    @Test    
    public void testFindById() {
    	Topping topping = toppingService.save(testToppings[0]);
    	Optional<Topping> savedTopping = toppingService.getTopping(topping.getId());
    	assertTrue(savedTopping.isPresent());
    }
    
    // Test	finding all toppings
    @Test    
    public void testFindAll() {
    	toppingService.save(testToppings[0]);
    	toppingService.save(testToppings[5]);
    	List<Topping> toppings = toppingService.getAllToppings();
    	assertEquals(2, toppings.size());
    }
    
    // Verify updating works properly
    @Test    
    public void testUpdate() {  
    	Topping topping = toppingService.save(testToppings[0]);
    	topping.setAmount(BigDecimal.valueOf(5));
    	toppingService.save(topping);
    	Optional<Topping> savedTopping = toppingService.getTopping(topping.getId());
    	assertTrue(savedTopping.isPresent() && savedTopping.get().getAmount().compareTo(topping.getAmount()) == 0);
    }
    
    // Test deleting a topping
    @Test    
    public void testDelete() {  
    	Topping topping = toppingService.save(testToppings[0]);
    	Optional<Topping> savedTopping = toppingService.getTopping(topping.getId());
    	assertTrue(savedTopping.isPresent());
    	toppingService.delete(savedTopping.get().getId());
    	savedTopping = toppingService.getTopping(topping.getId());
    	assertFalse(savedTopping.isPresent());
    }    
    
    // Validate order count default value is zero
    @Test   
    public void testOrderCountSetZero() {  
    	Topping topping = toppingService.save(testToppings[0]);
    	Optional<Topping> savedTopping = toppingService.getTopping(topping.getId());
    	assertEquals(0, savedTopping.get().getOrderCount());
    }    
    
    private void deleteAllObjects() {
    	toppingService.deleteAll();
    }
    
    private boolean compareCoffee(Topping a, Topping b) {
        if ((a == null) || (b == null)) {
            return false;
        }

        return ((a.getName().equals(b.getName()) &&
        		a.getAmount().compareTo(b.getAmount()) == 0));
    }
}
