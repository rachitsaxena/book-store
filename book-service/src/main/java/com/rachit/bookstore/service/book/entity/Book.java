package com.rachit.bookstore.service.book.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rachit.bookstore.service.book.component.LocalDateTimeDeserializer;
import com.rachit.bookstore.service.book.component.LocalDateTimeSerializer;


@Entity
public class Book {

	private Long id;
	private String title;
	private List<String> authors;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime publicationDate;
	private String publishingHouse;
	private List<String> tags;
	private UUID isbn;

	public Book() {
	}

	public Book(String title, List<String> authors, LocalDateTime publicationDate, String publishingHouse,
			List<String> tags, UUID isbn) {
		this.title = title;
		this.authors = authors;
		this.publicationDate = publicationDate;
		this.publishingHouse = publishingHouse;
		this.tags = tags;
		this.isbn = isbn;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@ElementCollection
	@CollectionTable(name = "authors")
	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
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

	@ElementCollection
	@CollectionTable(name = "tags")
	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public UUID getIsbn() {
		return isbn;
	}
	
	public void setIsbn(UUID isbn) {
		this.isbn = isbn;
	}
	
}
