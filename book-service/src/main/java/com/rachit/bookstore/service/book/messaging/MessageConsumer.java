package com.rachit.bookstore.service.book.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
// TODO: Remove this class. This was just for testing.
public class MessageConsumer {

	private final String QUEUE_NAME = "BookQueue";

	@Bean
	Queue queue() {
		return new Queue(QUEUE_NAME, true);
	}

	@RabbitListener(queues = QUEUE_NAME)
	public void processMessage(String message) {
		System.out.println("Book CRUD Message: " + message);
	}
}
