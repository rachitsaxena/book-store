package com.rachit.bookstore.service.search.messaging;

import java.io.IOException;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rachit.bookstore.service.search.entity.Book;
import com.rachit.bookstore.service.search.entity.messaging.MasterSyncBookDetails;
import com.rachit.bookstore.service.search.repository.BookRepository;

@Component
public class BookMasterEntityConsumer {

	private final String QUEUE_NAME = "BookQueue";
	private ObjectMapper mapper;
	
	@Autowired
	private MongodbBookDetailsProducer mongoDetailsProducer;
	
	@Autowired
	private BookRepository repository;

	public BookMasterEntityConsumer() {
		this.mapper = new ObjectMapper();
	}

	@Bean
	Queue queue() {
		return new Queue(QUEUE_NAME, true);
	}

	@RabbitListener(queues = QUEUE_NAME)
	public void processMessage(String message) {
		System.out.println(message);
		BookEventMessage eventMsg = null;
		try {
			eventMsg = mapper.readValue(message, BookEventMessage.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		switch (eventMsg.getEventType()) {
		case CREATE:
		case UPDATE:
			upsertBook(eventMsg.getBook());
			break;

		case DELETE:
			break;
		}
	}

	private void upsertBook(Book book) {
		Book mongoBook = repository.save(book);
		
		MasterSyncBookDetails detail = new MasterSyncBookDetails(
				mongoBook.getMasterBookId(), mongoBook.getMongoBookId());
		
		mongoDetailsProducer.publishEvent(detail);
	}
	
}
