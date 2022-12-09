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

import com.coffeetime.coffeeshop.domain.Topping;
import com.coffeetime.coffeeshop.exception.HttpClientErrorException;
import com.coffeetime.coffeeshop.exception.HttpNotFoundErrorException;
import com.coffeetime.coffeeshop.service.ToppingService;

@RestController
@RequestMapping("/toppings")
public class ToppingController {
	@Autowired
	private ToppingService toppingService;
	
    @PostMapping
    public ResponseEntity<Topping> createTopping(@RequestBody Topping topping) throws URISyntaxException {
        if (topping.getId() != null) {
            throw new HttpClientErrorException("A topping to be created cannot have id attribute");
        }
        topping.setOrderedCount(0);
        Topping result = toppingService.save(topping);
        return ResponseEntity.created(new URI("/api/toppings/" + result.getId())).body(result);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Topping> updateProduct(@PathVariable Long id, @RequestBody Topping topping) throws URISyntaxException {
        if (toppingService.getTopping(id) == null) {
            throw new HttpNotFoundErrorException("Cannot update because the topping with id " + id + " does not exist");
        }
        topping.setId(id);
        return ResponseEntity.ok(toppingService.save(topping));
    }
    
    @GetMapping
    public ResponseEntity<List<Topping>> getAllToppings() {
        return ResponseEntity.ok(toppingService.getAllToppings());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Topping> getTopping(@PathVariable Long id) {
    	Optional<Topping> topping = toppingService.getTopping(id);
        if (topping.isEmpty()) {
            throw new HttpNotFoundErrorException("Topping with id " + id + " does not exist");
        }
        return ResponseEntity.ok(topping.get());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Topping> deleteTopping(@PathVariable Long id) {
        if (toppingService.getTopping(id) == null) {
            throw new HttpNotFoundErrorException("Cannot delete because the topping with id " + id + " does not exist");
        }
        toppingService.delete(id);
        return ResponseEntity.ok().build();
    }
    
    /**
     * 
     * @param limit
     * @return
     * 
     * Get most popular toppings with number of toppings parameter
     */
    @GetMapping("/getTopNTopping/{limit}")
    public ResponseEntity<List<Topping>> getTopNTopping(@PathVariable int limit) {
    	return ResponseEntity.ok(toppingService.getTopN(limit));
    	
    }
}
