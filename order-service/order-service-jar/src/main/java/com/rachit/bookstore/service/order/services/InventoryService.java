package com.rachit.bookstore.service.order.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.rachit.bookstore.service.inventory.entity.Inventory;
import com.rachit.bookstore.service.order.proxy.InventoryServiceProxy;

@Component
public class InventoryService {

	@Autowired
	private InventoryServiceProxy serviceProxy;
	
	@HystrixCommand(fallbackMethod = "getDefaultInventory")
	public Inventory getInventory(Long sellerId, UUID isbn) {
		return serviceProxy.getInventory(sellerId, isbn);
	}
	
	public Inventory getDefaultInventory(Long sellerId, UUID isbn) {
		return new Inventory();
	}

}
