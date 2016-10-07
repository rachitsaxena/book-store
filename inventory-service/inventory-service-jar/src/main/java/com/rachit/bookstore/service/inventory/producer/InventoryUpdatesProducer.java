package com.rachit.bookstore.service.inventory.producer;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class InventoryUpdatesProducer {

	private RabbitMessagingTemplate template;
	private ObjectMapper mapper;
	private static final String QUEUE_NAME = "InventoryToMongoUpdateQueue";
	@Autowired
	public InventoryUpdatesProducer(RabbitMessagingTemplate template) {
		this.template = template;
		mapper = new ObjectMapper();
	}
	
	@Bean
	Queue queue() {
		return new Queue(QUEUE_NAME, true);
	}
	
	public void publishEvent(InventoryToSearchUpdateEvent event) {
		try {
			String msgString = mapper.writeValueAsString(event);
			
			System.out.println("InventoryUpdatesProducer.publishEvent()"
					+ ": Event Published:" + msgString);
			
			template.convertAndSend(QUEUE_NAME, msgString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}
}
