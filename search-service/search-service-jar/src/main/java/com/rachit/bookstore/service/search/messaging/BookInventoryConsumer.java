package com.rachit.bookstore.service.search.messaging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rachit.bookstore.service.search.entity.Book;
import com.rachit.bookstore.service.search.entity.Inventory;
import com.rachit.bookstore.service.search.repository.BookRepository;

@Component
public class BookInventoryConsumer {

	private ObjectMapper objectMapper;
	private ModelMapper modelMapper;
	private BookRepository bookRepository;
	private static final String QUEUE_NAME = "InventoryToMongoUpdateQueue";
	
	
	@Autowired
	public BookInventoryConsumer(BookRepository bookRepository) {
		this.bookRepository =bookRepository;
		objectMapper = new ObjectMapper();
		modelMapper = new ModelMapper();
	}
	
	@Bean
	Queue queue() {
		return new Queue(QUEUE_NAME, true);
	}

	@RabbitListener(queues = QUEUE_NAME)
	public void processMessage(String message) {
		System.out.println("BookInventoryConsumer.processMessage()"
				+ ": Event Consumed: "+message);
		
		try {
			InventoryToSearchUpdateEvent eventMsg = objectMapper.readValue(message, InventoryToSearchUpdateEvent.class);
			final Inventory inventory = eventMsg.getInventory();
			
			Book book = bookRepository.findByIsbn(inventory.getIsbn());
			List<Inventory> inventories = book.getInventories();
			if(inventories == null) {
				book.setInventories(new ArrayList<>());
			}
			
			switch (eventMsg.getEventType()) {
			case CREATE:
				book.getInventories().add(inventory);
				bookRepository.save(book);
				break;
			case UPDATE:
				Optional<Inventory> invRecord = book.getInventories().stream().filter(inv -> inv.getId() == inventory.getId()).findFirst();
				if(invRecord.isPresent()) {
					Inventory inventoryToUpdate = invRecord.get();
					modelMapper.map(inventory, inventoryToUpdate);
					bookRepository.save(book);
				}else {
					System.out.println("+++++++++++++++++ BookInventoryConsumer.processMessage() Update: Inventory Record not found +++++++++++++++++");
				}
				break;
			case DELETE:
				invRecord = book.getInventories().stream().filter(inv -> inv.getId() == inventory.getId()).findFirst();
				if(invRecord.isPresent()) {
					Inventory inventoryToUpdate = invRecord.get();
					book.getInventories().remove(inventoryToUpdate);
					bookRepository.save(book);
				}else {
					System.out.println("+++++++++++++++++ BookInventoryConsumer.processMessage() Delete: Inventory Record not found +++++++++++++++++");
				}

				break;
			}
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}
