package com.rachit.bookstore.service.inventory;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.rachit.bookstore.service.inventory.entity.BookType;
import com.rachit.bookstore.service.inventory.entity.Inventory;
import com.rachit.bookstore.service.inventory.entity.Sku;
import com.rachit.bookstore.service.inventory.repository.InventoryRepository;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
@EnableFeignClients
@EnableCircuitBreaker
@CrossOrigin
public class InventoryServiceApplication implements CommandLineRunner {

	@Autowired
	private InventoryRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	
	
	@Override
	public void run(String... arg0) throws Exception {
		List<Sku> skus = Arrays.asList(
				new Sku(BookType.PAPER_BACK, 5.5, 10),
				new Sku(BookType.EBOOK, 8.0, 8)
				);
		
		Inventory inventory = new Inventory(123L, UUID.randomUUID(), skus);
		
		repository.save(inventory);
		
		
	}
}
