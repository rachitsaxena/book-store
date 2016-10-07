package com.rachit.bookstore.service.profile.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum UserType {

	BUYER (0),
	SELLER (1);
	
	private int value;
	
	private UserType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	@JsonCreator
	public static UserType fromValue(String value) {
		try {
			return fromValue(Integer.parseInt(value));
		}catch (Exception e) {
			for (UserType type : values()) {
				if(type.name().equalsIgnoreCase(value)) {
					return type;
				}
			}
		}
		throw new RuntimeException("Invalid UserType value : "+value);
	}	
	
	public static UserType fromValue(int value) {
		for (UserType type : values()) {
			if(type.value == value) {
				return type;
			}
		}
		throw new RuntimeException("Invalid UserType value : "+value);
	}
	

	
	
	
	
}
