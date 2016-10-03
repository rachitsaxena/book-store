package com.rachit.bookstore.service.inventory.proxy.profile;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("profile-service") //This should be name of service that can be looked up in Eureka
@RibbonClient
public interface ProfileServiceProxy {

	@RequestMapping(value = "/users/userId/{userId}", method = RequestMethod.GET)
	public User findByUserId(@PathVariable ("userId") Long userId);
}
