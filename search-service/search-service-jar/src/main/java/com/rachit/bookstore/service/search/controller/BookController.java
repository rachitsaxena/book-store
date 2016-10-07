package com.rachit.bookstore.service.search.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rachit.bookstore.service.search.entity.Book;
import com.rachit.bookstore.service.search.repository.BookRepository;

@RestController
@RequestMapping(value = "/books")
public class BookController {

	@Autowired
	private BookRepository repository;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Book> getAllBooks() {
		return repository.findAll();
	}
	
	@RequestMapping(value = "/isbn/{isbn}", method = RequestMethod.GET)
	public Book findByISBN(@PathVariable("isbn") UUID isbn) {
		return repository.findByIsbn(isbn);
	}
	
}
