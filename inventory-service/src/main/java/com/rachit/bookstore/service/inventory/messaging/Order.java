package com.rachit.bookstore.service.inventory.messaging;

import java.time.LocalTime;
import java.util.UUID;

public class Order {

	private Long id;
	private Long sellerId;
	private Long buyerId;
	private LocalTime orderPlacedOn;
	private LocalTime deliveryTargetedOn;
	private UUID isbn;
	private BookType bookType;
	private OrderStatus status;

	public Order() {
	}

	public Order(Long sellerId, Long buyerId, LocalTime orderPlacedOn, LocalTime deliveryTargetedOn, UUID isbn,
			BookType bookType) {
		this.sellerId = sellerId;
		this.buyerId = buyerId;
		this.orderPlacedOn = orderPlacedOn;
		this.deliveryTargetedOn = deliveryTargetedOn;
		this.isbn = isbn;
		this.bookType = bookType;
		this.status = OrderStatus.NEW;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public LocalTime getOrderPlacedOn() {
		return orderPlacedOn;
	}

	public void setOrderPlacedOn(LocalTime orderPlacedOn) {
		this.orderPlacedOn = orderPlacedOn;
	}

	public LocalTime getDeliveryTargetedOn() {
		return deliveryTargetedOn;
	}

	public void setDeliveryTargetedOn(LocalTime deliveryTargetedOn) {
		this.deliveryTargetedOn = deliveryTargetedOn;
	}

	public UUID getIsbn() {
		return isbn;
	}

	public void setIsbn(UUID isbn) {
		this.isbn = isbn;
	}

	public BookType getBookType() {
		return bookType;
	}

	public void setBookType(BookType bookType) {
		this.bookType = bookType;
	}
	
	public OrderStatus getStatus() {
		return status;
	}
	
	public void setStatus(OrderStatus status) {
		this.status = status;
	}

}
