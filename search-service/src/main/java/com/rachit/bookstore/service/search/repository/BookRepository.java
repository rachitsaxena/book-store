package com.rachit.bookstore.service.search.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rachit.bookstore.service.search.entity.Book;

public interface BookRepository extends MongoRepository<Book, String> {

	public Book findByIsbn(UUID isbn);
}
