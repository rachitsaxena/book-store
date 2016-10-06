package com.rachit.bookstore.service.order.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OrderStatus {

	NEW(0), PACKED(1), SHIPPED(2), OUT_FOR_DELIVERY(3), DELIVERED(4), CANCELLED(5);

	private int value;

	private OrderStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	
	@JsonCreator
	public static OrderStatus fromValue(String value) {
		
		try {
			int val = Integer.parseInt(value);
			for (OrderStatus type : values()) {
				if (type.value == val) {
					return type;
				}
			}
		}catch (Exception e) {
			for (OrderStatus type : values()) {
				if(type.name().equalsIgnoreCase(value)) {
					return type;
				}
			}
		}
		
		throw new RuntimeException("Invalid OrderStatus value : " + value);
	}
 	
}
