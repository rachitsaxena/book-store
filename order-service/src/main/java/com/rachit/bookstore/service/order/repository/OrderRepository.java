package com.rachit.bookstore.service.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rachit.bookstore.service.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
