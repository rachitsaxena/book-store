package com.rachit.bookstore.service.search.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum BookType {
	EBOOK(0), PAPER_BACK(1);

	private int value;

	private BookType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	
	@JsonCreator
	public static BookType fromValue(String value) {
		try {
			fromValue(Integer.parseInt(value));
		}catch (Exception e) {
			for (BookType type : values()) {
				if(type.name().equalsIgnoreCase(value)) {
					return type;
				}
			}
		}
		
		throw new RuntimeException("Invalid BookType value : " + value);
	}
 	
	public static BookType fromValue(int value) {
		for (BookType type : values()) {
			if (type.value == value) {
				return type;
			}
		}
		throw new RuntimeException("Invalid BookType value : " + value);
	}
}
