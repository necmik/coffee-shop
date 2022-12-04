package com.coffeetime.coffeeshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coffeetime.coffeeshop.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
