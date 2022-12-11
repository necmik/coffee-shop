package com.coffeetime.coffeeshop.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coffeetime.coffeeshop.domain.Coffee;
import com.coffeetime.coffeeshop.repository.CoffeeRepository;

@Service
@Transactional
public class CoffeeService {
	
	Logger logger = LoggerFactory.getLogger(CoffeeService.class);
	
	@Autowired
	private CoffeeRepository coffeeRepository;
	
	public Coffee save(Coffee coffee) {
		Coffee saved = coffeeRepository.save(coffee);
        logger.info("Coffee {} saved with Id: {}", saved.getName(), saved.getId());
        
        return saved;
    }
	
	public void delete(Long coffeeId) {
		coffeeRepository.deleteById(coffeeId);
		logger.info("Coffee with Id {} deleted", coffeeId);
    }

    public Optional<Coffee> getCoffee(Long coffeeId) {
    	return coffeeRepository.findById(coffeeId);
    }

    public List<Coffee> getAllCoffees() {
        return coffeeRepository.findAll();
    }
    
    // For unit tests
    public void deleteAll() {
		coffeeRepository.deleteAll();
    }
}
