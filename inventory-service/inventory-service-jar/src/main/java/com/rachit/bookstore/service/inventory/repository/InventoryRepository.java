package com.rachit.bookstore.service.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rachit.bookstore.service.inventory.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	
}
