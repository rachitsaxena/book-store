package com.rachit.bookstore.service.search;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.rachit.bookstore.service.search.repository.BookRepository;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
@EnableRabbit
public class SearchServiceApplication implements CommandLineRunner {

	@Autowired
	private BookRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(SearchServiceApplication.class, args);
	}
	
	@Override
	public void run(String... arg0) throws Exception {
		// TODO: This is just to keep database clean after startup. 
		// Comment it out in production.
		repository.deleteAll();
	}
	
}
