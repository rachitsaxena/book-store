package com.rachit.bookstore.service.order.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.rachit.bookstore.service.order.proxy.ProfileServiceProxy;
import com.rachit.bookstore.service.profile.entity.User;

@Component
public class ProfileService {

	@Autowired
	private ProfileServiceProxy serviceProxy;
	
	@HystrixCommand(fallbackMethod = "getDefaultUser")
	public User findByUserId(Long userId) {
		return serviceProxy.findByUserId(userId);
	}
	
	public User getDefaultUser(Long userId) {
		return new User();
	}
}
