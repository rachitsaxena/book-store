package com.rachit.bookstore.service.order.entity.messaging;

import com.rachit.bookstore.commons.enums.EventType;
import com.rachit.bookstore.service.order.entity.Order;

public class OrderEvent {

	private Order order;
	private EventType eventType;
	
	public OrderEvent() {
	}
	
	public OrderEvent(Order order, EventType eventType) {
		this.order = order;
		this.eventType = eventType;
	}
	
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public EventType getEventType() {
		return eventType;
	}
	
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
}
