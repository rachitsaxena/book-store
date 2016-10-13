package com.rachit.bookstore.service.profile.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rachit.bookstore.service.profile.entity.User;
import com.rachit.bookstore.service.profile.repository.UserRepository;

@RestController
@RequestMapping(value = "users")
public class UserController {

	private UserRepository repository;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	public UserController(UserRepository repository) {
		this.repository = repository;
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<User> getAllUsers() {
		return repository.findAll();
	}
	
	@RequestMapping(value = "/userId/{userId}", method = RequestMethod.GET)
	public User findByUserId(@PathVariable ("userId") Long userId) {
		LOGGER.info("Getting User by id: "+userId);
		User user = repository.findOne(userId);
		
		LOGGER.info("Returning User by id :"+userId +" User :"+user);
		
		return user;
	}
	
	@RequestMapping(value = "/upsert", method = RequestMethod.POST)
	public User upsertUser(@RequestBody User user) {
		if(user.getUserType() == null ) {
			LOGGER.error("UserType cannot be null");
			throw new RuntimeException("UserType is requried.");
		}
		return repository.save(user);
	}
	
}
