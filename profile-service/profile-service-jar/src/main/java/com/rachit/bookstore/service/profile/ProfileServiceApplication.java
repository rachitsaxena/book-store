package com.rachit.bookstore.service.profile;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.rachit.bookstore.service.profile.entity.Address;
import com.rachit.bookstore.service.profile.entity.User;
import com.rachit.bookstore.service.profile.entity.UserType;
import com.rachit.bookstore.service.profile.repository.UserRepository;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
@CrossOrigin
public class ProfileServiceApplication implements CommandLineRunner {

	@Autowired
	private UserRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(ProfileServiceApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		Address buyerAddress = new Address("Laxmi Vani", "Hyderguda", "Hyderabad", "Telangana", "India", 500023, Boolean.TRUE);
		User buyer = new User("Rachit", "Saxena", UserType.BUYER, Arrays.asList(buyerAddress), "buyer@gmail.com");
		repository.save(buyer);
		
		Address sellerAddress = new Address("402, Midtown", "Banjara Hills", "Hyderabad", "Telangana", "India", 500034, Boolean.TRUE);
		User seller = new User("Optical", "World", UserType.SELLER, Arrays.asList(sellerAddress), "seller@gmail.com");
		repository.save(seller);
		
	}
}
