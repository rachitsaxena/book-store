package com.rachit.bookstore.service.order.controller;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rachit.bookstore.service.order.entity.BookType;
import com.rachit.bookstore.service.order.entity.Order;
import com.rachit.bookstore.service.book.entity.Book;
import com.rachit.bookstore.service.inventory.entity.Inventory;
import com.rachit.bookstore.service.inventory.entity.Sku;
import com.rachit.bookstore.service.order.services.BookService;
import com.rachit.bookstore.service.order.services.InventoryService;
import com.rachit.bookstore.service.order.services.ProfileService;
import com.rachit.bookstore.service.profile.entity.User;

@Component
public class OrderValidator {

	private ProfileService profileService;
	private InventoryService inventoryService;
	private BookService bookService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderValidator.class);
	
	@Autowired
	public OrderValidator(ProfileService profileService,
			InventoryService inventoryService,
			BookService bookService) {
		this.profileService = profileService;
		this.inventoryService = inventoryService;
		this.bookService = bookService;
	}
	
	/**
	 * Move these rules to command pattern.
	 * @param order
	 */
	public void validateOrder(Order order) {
		Long sellerId = order.getSellerId();
		Long buyerId = order.getBuyerId();
		UUID isbn = order.getIsbn();
		
		if(sellerId == null || sellerId == 0 
			|| buyerId == null || buyerId == 0 
			|| isbn == null) {
			String msg = "Order must contain Seller, Buyer and ISBN information";
			LOGGER.error(msg);
			throw new RuntimeException(msg);
		}
		
		User seller = profileService.findByUserId(sellerId);
		if(seller == null || seller.getId() <= 0) {
			String msg = "Either Seller is Invalid or Seller details not available at "
					+ "the moment. Select a different Seller. [SellerId:"+sellerId+"]";
			LOGGER.error(msg);
			throw new RuntimeException(msg);
		}
		
		User buyer = profileService.findByUserId(buyerId);
		if(buyer == null || buyer.getId() <= 0) {
			String msg = "Either Buyer details are Invalid or details are not available at "
					+ "the moment. Please try again later. [BuyerId:"+buyerId+"]";
			LOGGER.error(msg);
			throw new RuntimeException(msg);
		}
		
		if(buyer.getAddresses() == null || buyer.getAddresses().isEmpty()) {
			String msg = "Buyer has no delivery address information [BuyerId:"+buyerId+"]";
			LOGGER.error(msg);
			throw new RuntimeException(msg);
		}
		
		Book book = bookService.findBookByIsbn(isbn);
		if(book == null || book.getMasterBookId() <= 0) {
			String msg = "Invalid Book or Book Details not available at the moment. "
					+ "Please try again later. ISBN [ISBN:"+isbn+"]";
			LOGGER.error(msg);
			throw new RuntimeException(msg);
		}
		
		Inventory inventory = inventoryService.getInventory(sellerId, isbn);
		if(inventory == null || inventory.getId() == null || inventory.getId() <= 0 || inventory.getSkus() == null || inventory.getSkus().isEmpty()) {
			String msg = "Inventory Information not available or Seller do not have sufficient stock. [Inventory Empty]";
			LOGGER.error(msg);
			throw new RuntimeException(msg);
		}
		
		Optional<Sku> opSku = inventory.getSkus().stream().filter(sku -> BookType.fromValue(sku.getFormat().getValue()) == order.getBookType()).findFirst();
		if(!opSku.isPresent()) {
			String msg = "Seller do not have book in "+order.getBookType()+" format.";
			LOGGER.error(msg);
			throw new RuntimeException(msg);
		}else {
			Sku sku = opSku.get();
			if(sku.getAvailableQuantity() < 1) {
				String msg = "Seller do not have book in "+order.getBookType()+" format. [Stock not available]";
				LOGGER.error(msg);
				throw new RuntimeException(msg);
			}
		}
	}
}
