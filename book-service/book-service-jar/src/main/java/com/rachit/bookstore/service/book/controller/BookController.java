package com.rachit.bookstore.service.book.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rachit.bookstore.service.book.entity.Book;
import com.rachit.bookstore.service.book.messaging.BookEventMessage;
import com.rachit.bookstore.service.book.messaging.EventType;
import com.rachit.bookstore.service.book.messaging.MessageProducer;
import com.rachit.bookstore.service.book.repository.BookRepository;

@RestController
@RequestMapping(value = "/books")
public class BookController {

	private BookRepository repository;
	private MessageProducer producer;
	
	@Autowired
	public BookController(BookRepository repository, MessageProducer producer) {
		this.repository = repository;
		this.producer = producer;
	}
	
	@RequestMapping(value="/upsert", method = RequestMethod.POST)
	public Book createBook(@RequestBody Book book) {
		
		Book b = repository.findByTitle(book.getTitle());
		if(b != null && book.getMasterBookId() == null) {
			throw new RuntimeException("Book already exist");
		}
		EventType eventType = book.getMasterBookId() == null ? EventType.CREATE : EventType.UPDATE;
		
		Book bookUpserted = repository.save(book);
		producer.publishEvent(new BookEventMessage(bookUpserted, eventType));
		
		return bookUpserted;
	}
	
	
	@RequestMapping(value ="/all" , method = RequestMethod.GET)
	public List<Book> getAllBooks() {
		return repository.findAll();
	}
	
	@RequestMapping(value = "/delete/bookId/{bookId}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("bookId") Long bookId) {
		Book b = repository.getOne(bookId);
		if(b == null) {
			throw new RuntimeException("Book with id: "+bookId+" does not exist");
		}
		
		repository.delete(bookId);
		
		Book book = new Book();
		book.setIsbn(b.getIsbn());
		
		producer.publishEvent(new BookEventMessage(book, EventType.DELETE));
	}
	
	@RequestMapping(value = "/isbn/{isbn}", method = RequestMethod.GET)
	public Book findByIsbn(@PathVariable("isbn") UUID isbn) {
		return repository.findByIsbn(isbn);
	}
	
	
}
