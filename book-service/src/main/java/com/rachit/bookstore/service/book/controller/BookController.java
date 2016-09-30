package com.rachit.bookstore.service.book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rachit.bookstore.service.book.entity.Book;
import com.rachit.bookstore.service.book.repository.BookRepository;

@RestController
@RequestMapping(value = "/books")
public class BookController {

	private BookRepository repository;
	
	@Autowired
	public BookController(BookRepository repository) {
		this.repository = repository;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Book createBook(@RequestBody Book book) {
		if(book.getId() != null) {
			throw new RuntimeException("Book already exist");
		}
		
		Book b = repository.findByTitle(book.getTitle());
		if(b != null) {
			throw new RuntimeException("Book already exist");
		}
		
		return repository.save(book);
	}
	
	
	@RequestMapping(value ="/all" , method = RequestMethod.GET)
	public List<Book> getAllBooks() {
		return repository.findAll();
	}
	
}
