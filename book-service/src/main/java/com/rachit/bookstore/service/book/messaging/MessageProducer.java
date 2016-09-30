package com.rachit.bookstore.service.book.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MessageProducer {

	private RabbitMessagingTemplate template;
	private ObjectMapper mapper;
	
	@Autowired
	public MessageProducer(RabbitMessagingTemplate template) {
		this.template = template;
		mapper = new ObjectMapper();
	}
	
	@Bean
	Queue queue() {
		return new Queue("BookQueue", true);
	}
	
	public void publishEvent(BookEventMessage message) {
		try {
			String msgString = mapper.writeValueAsString(message);
			template.convertAndSend("BookQueue", msgString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			
		}
		
	}
	
}
