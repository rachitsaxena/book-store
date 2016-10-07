package com.rachit.bookstore.service.inventory.entity;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Inventory {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long sellerId;
	private UUID isbn;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "INVENTORY_ID")
	private List<Sku> skus;
	
	public Inventory() {
	}
	
	public Inventory(Long sellerId, UUID isbn, List<Sku> skus) {
		this.sellerId = sellerId;
		this.isbn = isbn;
		this.skus = skus;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getSellerId() {
		return sellerId;
	}
	
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	
	public List<Sku> getSkus() {
		return skus;
	}
	
	public UUID getIsbn() {
		return isbn;
	}
	
	public void setIsbn(UUID isbn) {
		this.isbn = isbn;
	}
	
	public void setSkus(List<Sku> skus) {
		this.skus = skus;
	}
	
}
