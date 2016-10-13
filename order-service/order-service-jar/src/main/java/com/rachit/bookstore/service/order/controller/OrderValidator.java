package com.rachit.bookstore.service.order.controller;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rachit.bookstore.service.order.entity.BookType;
import com.rachit.bookstore.service.order.entity.Order;
import com.rachit.bookstore.service.order.proxy.book.Book;
import com.rachit.bookstore.service.order.proxy.book.BookServiceProxy;
import com.rachit.bookstore.service.order.proxy.inventory.Inventory;
import com.rachit.bookstore.service.order.proxy.inventory.InventoryServiceProxy;
import com.rachit.bookstore.service.order.proxy.inventory.Sku;
import com.rachit.bookstore.service.order.proxy.profile.ProfileServiceProxy;
import com.rachit.bookstore.service.order.proxy.profile.User;

@Component
public class OrderValidator {

	private ProfileServiceProxy profileService;
	private InventoryServiceProxy inventoryService;
	private BookServiceProxy bookService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderValidator.class);
	
	@Autowired
	public OrderValidator(ProfileServiceProxy profileServiceProxy,
			InventoryServiceProxy inventoryServiceProxy,
			BookServiceProxy bookServiceProxy) {
		this.profileService = profileServiceProxy;
		this.inventoryService = inventoryServiceProxy;
		this.bookService = bookServiceProxy;
	}
	
	/**
	 * Move these rules to command pattern.
	 * @param order
	 */
	public void validateOrder(Order order) {
		Long sellerId = order.getSellerId();
		Long buyerId = order.getBuyerId();
		UUID isbn = order.getIsbn();
		BookType type = order.getBookType();
		
		if(sellerId == null || sellerId == 0 
			|| buyerId == null || buyerId == 0 
			|| isbn == null) {
			String msg = "Order must contain Seller, Buyer and ISBN information";
			LOGGER.error(msg);
			throw new RuntimeException(msg);
		}
		
		User seller = profileService.findByUserId(sellerId);
		if(seller == null ) {
			String msg = "Invalid Seller [SellerId:"+sellerId+"]";
			LOGGER.error(msg);
			throw new RuntimeException(msg);
		}
		
		User buyer = profileService.findByUserId(buyerId);
		if(buyer == null ) {
			String msg = "Invalid Buyer [BuyerId:"+buyerId+"]";
			LOGGER.error(msg);
			throw new RuntimeException(msg);
		}
		
		if(buyer.getAddresses() == null || buyer.getAddresses().isEmpty()) {
			String msg = "Buyer has no delivery address information [BuyerId:"+buyerId+"]";
			LOGGER.error(msg);
			throw new RuntimeException(msg);
		}
		
		Book book = bookService.findBookByIsbn(isbn);
		if(book == null) {
			String msg = "Invalid Book ISBN [ISBN:"+isbn+"]";
			LOGGER.error(msg);
			throw new RuntimeException(msg);
		}
		
		Inventory inventory = inventoryService.getInventory(sellerId, isbn);
		if(inventory == null || inventory.getSkus() == null || inventory.getSkus().isEmpty()) {
			String msg = "Seller do not have sufficient stock. [Inventory Empty]";
			LOGGER.error(msg);
			throw new RuntimeException(msg);
		}
		
		Optional<Sku> opSku = inventory.getSkus().stream().filter(sku -> sku.getFormat() == type).findFirst();
		if(!opSku.isPresent()) {
			String msg = "Seller do not have book in "+type+" format.";
			LOGGER.error(msg);
			throw new RuntimeException(msg);
		}else {
			Sku sku = opSku.get();
			if(sku.getAvailableQuantity() < 1) {
				String msg = "Seller do not have book in "+type+" format. [Stock not available]";
				LOGGER.error(msg);
				throw new RuntimeException(msg);
			}
		}
	}
}
