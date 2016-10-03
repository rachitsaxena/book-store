package com.rachit.bookstore.service.book.messaging;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum EventType {

	CREATE(0), UPDATE(1), DELETE(2);

	private int value;

	private EventType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@JsonCreator
	public static EventType fromValue(String value) {
		try {

			int val = Integer.parseInt(value);

			for (EventType type : values()) {
				if (val == type.value) {
					return type;
				}
			}
		} catch (Exception e) {
			for (EventType type : values()) {
				if (type.name().equalsIgnoreCase(value)) {
					return type;
				}
			}
		}

		throw new RuntimeException("Invalid EventType value : " + value);
	}
}
