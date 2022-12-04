package com.coffeetime.coffeeshop.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coffeetime.coffeeshop.domain.Topping;
import com.coffeetime.coffeeshop.repository.ToppingRepository;

@Service
@Transactional
public class ToppingService {
	
	Logger logger = LoggerFactory.getLogger(ToppingService.class);
	
	@Autowired
	private ToppingRepository toppingRepository;
	
	public Topping save(Topping topping) {
		Topping saved = toppingRepository.save(topping);
        logger.info("Topping {} saved with Id: {}", saved.getName(), saved.getId());
        return saved;
    }
	
	public void delete(Long toppingId) {
		toppingRepository.deleteById(toppingId);
		logger.info("Topping with Id {} deleted", toppingId);
    }

    public Optional<Topping> getTopping(Long toppingId) {
        return toppingRepository.findById(toppingId);
    }

    public List<Topping> getAllToppings() {
        return toppingRepository.findAll();
    }
    
    public List<Topping> getTopN(int limit) {
        return toppingRepository.findTopN(limit);
    }
}
