package com.rachit.bookstore.service.search.messaging;

import com.rachit.bookstore.service.search.entity.Book;

public class BookEventMessage {
	
	private BookEventType eventType;
	private Book book;
	
	public BookEventMessage() {
	}
	
	public BookEventMessage(Book book, BookEventType eventType) {
		this.book = book;
		this.eventType = eventType;
	}

	
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	
	public BookEventType getEventType() {
		return eventType;
	}
	
	public void setEventType(BookEventType eventType) {
		this.eventType = eventType;
	}
}
