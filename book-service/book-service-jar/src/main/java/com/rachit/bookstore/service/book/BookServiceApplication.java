package com.rachit.bookstore.service.book;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.rachit.bookstore.service.book.entity.Book;
import com.rachit.bookstore.service.book.repository.BookRepository;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
@EnableRabbit
@CrossOrigin
public class BookServiceApplication implements CommandLineRunner, RabbitListenerConfigurer {

	@Autowired 
	private BookRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(BookServiceApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		repository.deleteAll();
		repository.save(new Book("Hibernate Book", Arrays.asList("Rachit", "Saxena"),  LocalDateTime.now(), 
				"Pramati", Arrays.asList("Hibernate", "Java"), UUID.randomUUID(), 200.0));
		
		repository.save(new Book("Spring Book", Arrays.asList("Ritesh", "Prasad"), LocalDateTime.now().minusYears(1), 
				"Imaginea", Arrays.asList("Spring", "Java"), UUID.randomUUID(), 150.0));
		
		repository.save(new Book("Spring Microservices", Arrays.asList("Rajesh", "Srikanth"), 
				LocalDateTime.now().minusYears(1).minusMonths(6), "PackPub", 
				Arrays.asList("Spring", "Cloud", "Microservices"), UUID.randomUUID(), 500.0));
	}

	@Bean
	public MappingJackson2MessageConverter jackson2Converter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		return converter;
	}

	@Bean
	public DefaultMessageHandlerMethodFactory myHandlerMethodFactory() {
		DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
		factory.setMessageConverter(jackson2Converter());
		return factory;
	}

	@Override
	public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(myHandlerMethodFactory());
	}	
	
}
