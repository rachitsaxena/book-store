package com.rachit.bookstore.service.order.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.rachit.bookstore.service.book.entity.Book;
import com.rachit.bookstore.service.order.proxy.BookServiceProxy;

@Component
public class BookService {

	@Autowired
	private BookServiceProxy serviceProxy;
	
	@HystrixCommand(fallbackMethod = "getDefaultBook")
	public Book findBookByIsbn(UUID isbn) {
		return serviceProxy.findBookByIsbn(isbn);
	}
	
	public Book getDefaultBook(UUID isbn) {
		return new Book();
	}
}
