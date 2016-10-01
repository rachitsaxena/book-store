package com.rachit.bookstore.service.profile.controller;

import java.util.List;

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
		return repository.findOne(userId);
	}
	
	@RequestMapping(value = "/upsert", method = RequestMethod.POST)
	public User findByUserId(@RequestBody User user) {
		if(user.getUserType() == null ) {
			throw new RuntimeException("UserType is requried.");
		}
		return repository.save(user);
	}
	
}
