package com.rachit.bookstore.service.book;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.rachit.bookstore.service.book.entity.Book;
import com.rachit.bookstore.service.book.repository.BookRepository;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
public class BookServiceApplication implements CommandLineRunner {

	@Autowired
	private BookRepository repository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(BookServiceApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		repository.deleteAll();
		repository.save(new Book("Hibernate Book", Arrays.asList("Rachit", "Saxena"), 
				LocalDateTime.now(), "Pramati", Arrays.asList("Hibernate", "Java")));
		
		repository.save(new Book("Spring Book", Arrays.asList("Ritesh", "Prasad"), 
				LocalDateTime.now().minusYears(1), "Imaginea", Arrays.asList("Spring", "Java")));
		
		repository.save(new Book("Spring Microservices", Arrays.asList("Rajesh", "Srikanth"), 
				LocalDateTime.now().minusYears(1).minusMonths(6), "PackPub", 
				Arrays.asList("Spring", "Cloud", "Microservices")));
	}
	
}
