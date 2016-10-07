package com.rachit.bookstore.service.inventory.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rachit.bookstore.commons.enums.EventType;
import com.rachit.bookstore.service.inventory.entity.Inventory;
import com.rachit.bookstore.service.inventory.producer.InventoryToSearchUpdateEvent;
import com.rachit.bookstore.service.inventory.producer.InventoryUpdatesProducer;
import com.rachit.bookstore.service.inventory.proxy.book.BookServiceProxy;
import com.rachit.bookstore.service.inventory.proxy.profile.ProfileServiceProxy;
import com.rachit.bookstore.service.inventory.proxy.profile.User;
import com.rachit.bookstore.service.inventory.proxy.profile.UserType;
import com.rachit.bookstore.service.inventory.repository.InventoryRepository;

@RestController
@RequestMapping(value = "/inventory")
public class InventoryController {

	private InventoryRepository repository;
	private BookServiceProxy bookServiceProxy;
	private ProfileServiceProxy profileServiceProxy;
	private InventoryUpdatesProducer inventoryUpdatesProducer;
	
	@Autowired
	public InventoryController(InventoryRepository repository,
			BookServiceProxy bookServiceProxy,
			ProfileServiceProxy profileServiceProxy,
			InventoryUpdatesProducer inventoryUpdatesProducer) {
		this.repository = repository;
		this.bookServiceProxy = bookServiceProxy;
		this.profileServiceProxy = profileServiceProxy;
		this.inventoryUpdatesProducer = inventoryUpdatesProducer;
	}
	
	@RequestMapping(value = "/inventory/sellerId/{sellerId}", method = RequestMethod.GET)
	public List<Inventory> getSellerInventories(@PathVariable("sellerId") Long sellerId) {
		Inventory critera = new Inventory();
		critera.setSellerId(sellerId);
		Example<Inventory> example = Example.of(critera);
		return repository.findAll(example);
	}
	
	@RequestMapping(value = "upsert/sellerId/{sellerId}/isbn/{isbn}", method = RequestMethod.POST)
	public Inventory upsertInventory(@RequestBody Inventory inventory, 
			@PathVariable("sellerId")Long sellerId, @PathVariable("isbn") UUID isbn) {

		if( !(isValidBook(isbn) && isValidSeller(sellerId)) ) {
			throw new RuntimeException("Seller with id ["+sellerId+"] is not a valid seller");
		}
		
		inventory.setIsbn(isbn);
		inventory.setSellerId(sellerId);
		
		EventType eventType = (inventory.getId() == null || inventory.getId() == 0) ? EventType.CREATE : EventType.UPDATE;
		Inventory inv = repository.save(inventory);

		inventoryUpdatesProducer.publishEvent(new InventoryToSearchUpdateEvent(eventType, inv));
		
		return inv;
	}

	private boolean isValidSeller(Long sellerId) {
		User user = profileServiceProxy.findByUserId(sellerId);
		
		if(user == null || (user.getUserType() != UserType.SELLER)) {
			return false;
		}
		
		return true;
	}

	private boolean isValidBook(UUID isbn) {
		return bookServiceProxy.findBookByIsbn(isbn) != null;
	}

	@RequestMapping(value = "/sellerId/{sellerId}/isbn/{isbn}", method = RequestMethod.GET)
	public Inventory getInventory(@PathVariable("sellerId")Long sellerId, @PathVariable("isbn") UUID isbn) {
		Inventory critera = new Inventory();
		critera.setSellerId(sellerId);
		critera.setIsbn(isbn);
		Example<Inventory> example = Example.of(critera);
		return repository.findOne(example);
	}
	
}
