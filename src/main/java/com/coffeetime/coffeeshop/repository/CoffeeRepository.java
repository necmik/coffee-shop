package com.coffeetime.coffeeshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coffeetime.coffeeshop.domain.Coffee;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long>{

}
