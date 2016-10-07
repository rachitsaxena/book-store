package com.rachit.bookstore.service.order.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rachit.bookstore.service.order.entity.messaging.OrderEvent;

@Component
@EnableBinding(Source.class)
public class OrderEventPublisher {

	private Source source;
	private ObjectMapper mapper;
	
	@Autowired
	public OrderEventPublisher(Source source) {
		this.source = source;
		this.mapper = Jackson2ObjectMapperBuilder.json()
	            .serializationInclusion(Include.NON_NULL)
	            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
	            .modules(new JavaTimeModule())
	            .build();
	}
	
	
	public void publishEvent(OrderEvent event) {
		System.out.println("OrderEventPublisher.publishEvent(): Order Event Created: "+event);
		try {
			String json = mapper.writeValueAsString(event);
			source.output().send(MessageBuilder.withPayload(json).build());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	
}
