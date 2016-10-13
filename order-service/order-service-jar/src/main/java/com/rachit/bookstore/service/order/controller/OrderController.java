package com.rachit.bookstore.service.order.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rachit.bookstore.commons.enums.EventType;
import com.rachit.bookstore.service.order.controller.request.OrderQuery;
import com.rachit.bookstore.service.order.entity.Order;
import com.rachit.bookstore.service.order.entity.messaging.OrderEvent;
import com.rachit.bookstore.service.order.messaging.OrderEventPublisher;
import com.rachit.bookstore.service.order.repository.OrderRepository;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

	private OrderRepository orderRepository;
	private OrderValidator orderValidator;
	private OrderEventPublisher publisher;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	public OrderController(OrderRepository orderRepository, OrderValidator validator, OrderEventPublisher publisher) {
		this.orderRepository = orderRepository;
		this.orderValidator = validator;
		this.publisher = publisher;
	}
	
	@RequestMapping(value = "/upsert", method = RequestMethod.POST)
	public Order upsertOrder(@RequestBody Order order) {
		LOGGER.info("Order upsert request : "+order);
		orderValidator.validateOrder(order);
		
		EventType eventType = (order.getId() == null || order.getId() == 0) ? EventType.CREATE : EventType.UPDATE;
		
		Order orderUpserted = orderRepository.save(order);
		publisher.publishEvent(new OrderEvent(orderUpserted, eventType));
		
		return orderUpserted;
	}
	
	@RequestMapping(value = "/Buyer/buyerId/{buyerId}", method = RequestMethod.PUT)
	public List<Order> findOrdersByBuyerId(@PathVariable("buyerId") Long buyerId, @RequestBody OrderQuery orderQuery) {
		return null;
	}
	
	@RequestMapping(value = "/Buyer/sellerId/{sellerId}", method = RequestMethod.PUT)
	public List<Order> findOrdersBySellerId(@PathVariable("sellerId") Long sellerId, @RequestBody OrderQuery orderQuery) {
		return null;
	}
	
}
