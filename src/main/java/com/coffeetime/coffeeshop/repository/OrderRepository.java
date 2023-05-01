package com.coffeetime.coffeeshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coffeetime.coffeeshop.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}
