package com.rachit.bookstore.service.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rachit.bookstore.service.book.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	public Book findByTitle(String title);
	
}
