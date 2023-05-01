package com.coffeetime.coffeeshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.coffeetime.coffeeshop.domain.Topping;

@Repository
public interface ToppingRepository extends JpaRepository<Topping, Long> {
	
	@Query(value = "SELECT TOP ?1 a.* FROM Topping a ORDER BY a.order_count DESC", nativeQuery = true)
	List<Topping> findTopN(@Param("limit") int limit);
}
