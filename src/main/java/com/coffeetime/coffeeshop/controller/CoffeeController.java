package com.coffeetime.coffeeshop.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coffeetime.coffeeshop.domain.Coffee;
import com.coffeetime.coffeeshop.exception.HttpClientErrorException;
import com.coffeetime.coffeeshop.exception.HttpNotFoundErrorException;
import com.coffeetime.coffeeshop.service.CoffeeService;

@RestController
@RequestMapping("/api/coffees")
public class CoffeeController {	
	
	@Autowired
	private CoffeeService coffeeService;
	
    @PostMapping
    public ResponseEntity<Coffee> createProduct(@RequestBody Coffee coffee) throws URISyntaxException {
        if (coffee.getId() != null) {
            throw new HttpClientErrorException("A coffee to be created cannot have id attribute");
        }
        Coffee result = coffeeService.save(coffee);
        return ResponseEntity.created(new URI("/api/coffees/" + result.getId())).body(result);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Coffee> updateProduct(@PathVariable Long id, @RequestBody Coffee coffee) throws URISyntaxException {
        if (coffeeService.getCoffee(id) == null) {
            throw new HttpNotFoundErrorException("Cannot update because the coffee with id " + id + " does not exist");
        }
        coffee.setId(id);
        return ResponseEntity.ok(coffeeService.save(coffee));
    }
    
    @GetMapping
    public ResponseEntity<List<Coffee>> getAllCoffees() {
        return ResponseEntity.ok(coffeeService.getAllCoffees());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Coffee> getCoffee(@PathVariable Long id) {
    	Optional<Coffee> coffee = coffeeService.getCoffee(id);
        if (coffee.isEmpty()) {
            throw new HttpNotFoundErrorException("Coffee with id " + id + " does not exist");
        }
        return ResponseEntity.ok(coffee.get());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Coffee> deleteCoffee(@PathVariable Long id) {
        if (coffeeService.getCoffee(id) == null) {
            throw new HttpNotFoundErrorException("Cannot delete because the coffee with id " + id + " does not exist");
        }
        coffeeService.delete(id);
        return ResponseEntity.ok().build();
    }

}
