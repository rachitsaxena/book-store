package com.rachit.bookstore.service.inventory.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rachit.bookstore.commons.enums.EventType;
import com.rachit.bookstore.service.book.entity.Book;
import com.rachit.bookstore.service.inventory.entity.Inventory;
import com.rachit.bookstore.service.inventory.producer.InventoryToSearchUpdateEvent;
import com.rachit.bookstore.service.inventory.producer.InventoryUpdatesProducer;
import com.rachit.bookstore.service.inventory.repository.InventoryRepository;
import com.rachit.bookstore.service.inventory.service.BookService;
import com.rachit.bookstore.service.inventory.service.ProfileService;
import com.rachit.bookstore.service.profile.entity.User;
import com.rachit.bookstore.service.profile.entity.UserType;

@RestController
@RequestMapping(value = "/inventory")
public class InventoryController {

	private InventoryRepository repository;
	private BookService bookService;
	private ProfileService profileService;
	private InventoryUpdatesProducer inventoryUpdatesProducer;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InventoryController.class);
	
	@Autowired
	public InventoryController(InventoryRepository repository,
			BookService bookService,
			ProfileService profileService,
			InventoryUpdatesProducer inventoryUpdatesProducer) {
		this.repository = repository;
		this.bookService = bookService;
		this.profileService = profileService;
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

		if(!isValidBook(isbn)) {
			throw new RuntimeException("Book with isbn ["+isbn+"] not found");
		}
		
		if(!isValidSeller(sellerId)) {
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
		User user = profileService.findByUserId(sellerId);
		
		if(user == null || user.getId() == null || user.getId() <= 0 || (user.getUserType() != UserType.SELLER)) {
			return false;
		}
		
		return true;
	}

	private boolean isValidBook(UUID isbn) {
		Book book = bookService.findBookByIsbn(isbn);
		return  (book != null && book.getMasterBookId() != null && book.getMasterBookId() > 0);
	}

	@RequestMapping(value = "/sellerId/{sellerId}/isbn/{isbn}", method = RequestMethod.GET)
	public Inventory getInventory(@PathVariable("sellerId")Long sellerId, @PathVariable("isbn") UUID isbn) {
		LOGGER.info("Finding inventory for SellerId: "+sellerId+" and ISBN: "+isbn);
		Inventory critera = new Inventory();
		critera.setSellerId(sellerId);
		critera.setIsbn(isbn);
		Example<Inventory> example = Example.of(critera);
		return repository.findOne(example);
	}
	
}
