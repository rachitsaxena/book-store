package com.rachit.bookstore.service.search.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rachit.bookstore.service.search.entity.messaging.MasterSyncBookDetails;

/**
 * Sync back mongo db generated ID to book master repository.
 * 
 * @author rachits
 *
 */
@Component
public class MongodbBookDetailsProducer {

	private RabbitMessagingTemplate template;
	private ObjectMapper mapper;
	private final String QUEUE_NAME = "MongoBookDetailsQueue";
	
	@Autowired
	public MongodbBookDetailsProducer(RabbitMessagingTemplate template) {
		this.template = template;
		mapper = new ObjectMapper();
	}
	
	@Bean
	Queue queue() {
		return new Queue(QUEUE_NAME, true);
	}
	
	public void publishEvent(MasterSyncBookDetails details) {
		try {
			String msgString = mapper.writeValueAsString(details);
			template.convertAndSend(QUEUE_NAME, msgString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
