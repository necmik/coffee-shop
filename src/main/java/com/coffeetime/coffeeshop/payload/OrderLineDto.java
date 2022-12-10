package com.coffeetime.coffeeshop.payload;

import java.util.Set;

public class OrderLineDto {
	private Long coffeeId;
	private Set<Long> toppingIds;
	
	public Long getCoffeeId() {
		return coffeeId;
	}
	public void setCoffeeId(Long coffeeId) {
		this.coffeeId = coffeeId;
	}
	public Set<Long> getToppingIds() {
		return toppingIds;
	}
	public void setToppingIds(Set<Long> toppingIds) {
		this.toppingIds = toppingIds;
	}
	
	
}
