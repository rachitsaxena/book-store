package com.rachit.bookstore.service.profile.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rachit.bookstore.service.profile.entity.BuyerProfile;
import com.rachit.bookstore.service.profile.entity.Profile;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/profiles")
public class ProfileController {

	@RequestMapping(value = "/userId/{userId}", method = RequestMethod.GET)
	@ApiOperation(value = "Finds a (Buyer/Seller) profile", notes = "API to find a profile", response = Profile.class)
	public Profile findProfile(Long userId) {
		return new BuyerProfile();
	}
}
