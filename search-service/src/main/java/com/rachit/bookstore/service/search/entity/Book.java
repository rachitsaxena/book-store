package com.rachit.bookstore.service.search.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rachit.bookstore.service.search.component.LocalDateTimeDeserializer;
import com.rachit.bookstore.service.search.component.LocalDateTimeSerializer;

@Document(collection = "books")
public class Book {

	@Id
	private String id;
	/** Book ID from master record*/
	private Long bookId;
	private String title;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime publicationDate;
	private String publishingHouse;
	
	@Indexed
	private UUID isbn;
	private Double retailPrice;
	
	private List<String> authors;
	private List<String> tags;
	
	public Book() {
	}
	
	public Book(String title, LocalDateTime publicationDate, String publishingHouse, 
			UUID isbn, Double retailPrice, List<String> authors, List<String> tags) {
		this.title = title;
		this.publicationDate = publicationDate;
		this.publishingHouse = publishingHouse;
		this.isbn = isbn;
		this.retailPrice = retailPrice;
		this.authors = authors;
		this.tags = tags;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Long getBookId() {
		return bookId;
	}
	
	public void setMasterRecordId(Long bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDateTime getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(LocalDateTime publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getPublishingHouse() {
		return publishingHouse;
	}

	public void setPublishingHouse(String publishingHouse) {
		this.publishingHouse = publishingHouse;
	}

	public UUID getIsbn() {
		return isbn;
	}

	public void setIsbn(UUID isbn) {
		this.isbn = isbn;
	}

	public Double getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(Double retailPrice) {
		this.retailPrice = retailPrice;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	
	
	
}
