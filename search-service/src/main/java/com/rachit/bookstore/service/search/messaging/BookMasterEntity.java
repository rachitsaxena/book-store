package com.rachit.bookstore.service.search.messaging;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class BookMasterEntity {
	
	private Long id;
	private String title;
	private List<String> authors;
	private LocalDateTime publicationDate;
	private String publishingHouse;
	private List<String> tags;
	private UUID isbn;
	private Double retailPrice;
	
	public BookMasterEntity() {
	}

	public BookMasterEntity(String title, List<String> authors, LocalDateTime publicationDate, String publishingHouse,
			List<String> tags, UUID isbn, Double retailPrice) {
		this.title = title;
		this.authors = authors;
		this.publicationDate = publicationDate;
		this.publishingHouse = publishingHouse;
		this.tags = tags;
		this.isbn = isbn;
		this.retailPrice = retailPrice;
	}

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
	
	public Double getRetailPrice() {
		return retailPrice;
	}
	
	public void setRetailPrice(Double retailPrice) {
		this.retailPrice = retailPrice;
	}
}
