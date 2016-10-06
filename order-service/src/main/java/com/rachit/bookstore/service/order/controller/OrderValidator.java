package com.rachit.bookstore.service.order.controller;

import java.util.Optional;
import java.util.UUID;

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
			throw new RuntimeException("Order must contain Seller, Buyer and ISBN information");
		}
		
		User seller = profileService.findByUserId(sellerId);
		if(seller == null ) {
			throw new RuntimeException("Invalid Seller [SellerId:"+sellerId+"]");
		}
		
		User buyer = profileService.findByUserId(buyerId);
		if(buyer == null ) {
			throw new RuntimeException("Invalid Buyer [BuyerId:"+buyerId+"]");
		}
		
		if(buyer.getAddresses() == null || buyer.getAddresses().isEmpty()) {
			throw new RuntimeException("Buyer has no delivery address information [BuyerId:"+buyerId+"]");
		}
		
		Book book = bookService.findBookByIsbn(isbn);
		if(book == null) {
			throw new RuntimeException("Invalid Book ISBN [ISBN:"+isbn+"]");
		}
		
		Inventory inventory = inventoryService.getInventory(sellerId, isbn);
		if(inventory == null || inventory.getSkus() == null || inventory.getSkus().isEmpty()) {
			throw new RuntimeException("Seller do not have sufficient stock. [Inventory Empty]");
		}
		
		Optional<Sku> opSku = inventory.getSkus().stream().filter(sku -> sku.getFormat() == type).findFirst();
		if(!opSku.isPresent()) {
			throw new RuntimeException("Seller do not have book in "+type+" format.");
		}else {
			Sku sku = opSku.get();
			if(sku.getAvailableQuantity() < 1) {
				throw new RuntimeException("Seller do not have book in "+type+" format. [Stock not available]");
			}
		}
	}
}
