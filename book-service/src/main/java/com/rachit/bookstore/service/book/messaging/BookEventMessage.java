package com.rachit.bookstore.service.book.messaging;

import com.rachit.bookstore.service.book.entity.Book;

public class BookEventMessage {
	
	private EventType eventType;
	private Book book;
	
	public BookEventMessage(Book book, EventType eventType) {
		this.book = book;
		this.eventType = eventType;
	}

	
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	
	public EventType getEventType() {
		return eventType;
	}
	
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
}
