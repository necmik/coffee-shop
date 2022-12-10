package com.coffeetime.coffeeshop.payload;

import java.util.Set;

public class OrderDto {
	private Set<OrderLineDto> orderLines;

	public Set<OrderLineDto> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(Set<OrderLineDto> orderLines) {
		this.orderLines = orderLines;
	}
}
