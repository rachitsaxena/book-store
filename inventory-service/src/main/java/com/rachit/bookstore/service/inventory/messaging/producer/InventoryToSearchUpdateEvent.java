package com.rachit.bookstore.service.inventory.messaging.producer;

import com.rachit.bookstore.service.inventory.entity.Inventory;
import com.rachit.bookstore.service.inventory.messaging.EventType;

public class InventoryToSearchUpdateEvent {

	private EventType eventType;
	private Inventory inventory;
	
	public InventoryToSearchUpdateEvent() {
	}
	
	public InventoryToSearchUpdateEvent(EventType eventType, Inventory inventory) {
		this.eventType = eventType;
		this.inventory = inventory;
	}
	
	public EventType getEventType() {
		return eventType;
	}
	
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
}