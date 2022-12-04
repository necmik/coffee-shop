package com.coffeetime.coffeeshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coffeetime.coffeeshop.domain.Coffee;

public interface CoffeeRepository extends JpaRepository<Coffee, Long>{

}
