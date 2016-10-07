package com.rachit.bookstore.service.book.messaging;

import java.io.IOException;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rachit.bookstore.service.book.entity.Book;
import com.rachit.bookstore.service.book.repository.BookRepository;

@Component
public class MongoBookDetailsConsumer {

	private BookRepository repository;
	private final String QUEUE_NAME = "MongoBookDetailsQueue";
	private ObjectMapper mapper;
	
	@Autowired
	public MongoBookDetailsConsumer(BookRepository repository) {
		this.repository = repository;
		this.mapper = new ObjectMapper();
	}

	@Bean
	Queue queue() {
		return new Queue(QUEUE_NAME, true);
	}

	@RabbitListener(queues = QUEUE_NAME)
	public void processMessage(String message) {
		System.out.println("MongoBookDetailsConsumer.processMessage():::: "+message);
		MasterSyncBookDetails eventMsg = null;
		try {
			eventMsg = mapper.readValue(message, MasterSyncBookDetails.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			syncBackDetails(eventMsg);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional(value = TxType.REQUIRES_NEW)
	private void syncBackDetails(MasterSyncBookDetails eventMsg) {
		Book book = repository.findOne(eventMsg.getMasterBookId());
		book.setMongoBookId(eventMsg.getMongoBookId());
		repository.save(book);
	}
	
}
