package com.rachit.bookstore.service.book.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rachit.bookstore.service.book.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	public Book findByTitle(String title);
	public Book findByIsbn(UUID isbn);
	
	
}
