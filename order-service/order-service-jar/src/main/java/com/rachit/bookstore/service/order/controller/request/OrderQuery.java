package com.rachit.bookstore.service.order.controller.request;

import java.time.LocalTime;

import com.rachit.bookstore.service.order.entity.OrderStatus;

public class OrderQuery {

	private LocalTime ordersFrom;
	private LocalTime ordersUpto;
	private OrderStatus status;

	public OrderQuery() {
	}

	public OrderQuery(LocalTime from, LocalTime upto, OrderStatus status) {
		this.ordersFrom = from;
		this.ordersUpto = upto;
		this.status = status;
	}

	public LocalTime getOrdersFrom() {
		return ordersFrom;
	}

	public void setOrdersFrom(LocalTime ordersFrom) {
		this.ordersFrom = ordersFrom;
	}

	public LocalTime getOrdersUpto() {
		return ordersUpto;
	}

	public void setOrdersUpto(LocalTime ordersUpto) {
		this.ordersUpto = ordersUpto;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

}
