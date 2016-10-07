package com.rachit.bookstore.service.inventory.consumer;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.data.domain.Example;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rachit.bookstore.commons.enums.EventType;
import com.rachit.bookstore.service.inventory.entity.Inventory;
import com.rachit.bookstore.service.inventory.entity.Sku;
import com.rachit.bookstore.service.inventory.repository.InventoryRepository;
import com.rachit.bookstore.service.order.entity.Order;
import com.rachit.bookstore.service.order.entity.messaging.OrderEvent;

@Component
@EnableBinding(Sink.class)
public class OrderEventConsumer {

	private ObjectMapper mapper;
	private InventoryRepository inventoryRepository;
	
	public OrderEventConsumer(InventoryRepository repository) {
		this.inventoryRepository = repository;
		this.mapper = Jackson2ObjectMapperBuilder.json()
	            .serializationInclusion(Include.NON_NULL)
	            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
	            .modules(new JavaTimeModule())
	            .build();
	}
	
	@ServiceActivator(inputChannel = Sink.INPUT)
	public void processEvent(String message) {
		System.out.println("OrderEventConsumer.processEvent(): Order Event Received");
		try {
			OrderEvent event = mapper.readValue(message, OrderEvent.class);
			
			if(event.getEventType() == EventType.CREATE) {
				updateInventory(event.getOrder());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void updateInventory(Order order) {
		Inventory inventory = findInventory(order.getSellerId(), order.getIsbn());
		//TODO: Here order.bookType and sku.format both are same but differs in packaging. 
		// If included as maven dependency, both will have same package.
		Optional<Sku> skuOp = inventory.getSkus().stream().filter(s -> s.getFormat().getValue() == order.getBookType().getValue()).findFirst();
		if(skuOp.isPresent()) {
			Sku sku = skuOp.get();
			// TODO: Order can have only one book ? :) Add quantity to Order
			sku.setAvailableQuantity(sku.getAvailableQuantity() - 1);
			inventoryRepository.save(inventory);
		}else {
			System.err.println("ERROR: Sku not found for order :Seller: "+order.getSellerId() +" ISBN: "+order.getIsbn()+" Format:"+order.getBookType());
		}
	}

	private Inventory findInventory(Long sellerId, UUID isbn) {
		Inventory critera = new Inventory();
		critera.setSellerId(sellerId);
		critera.setIsbn(isbn);
		Example<Inventory> example = Example.of(critera);
		return inventoryRepository.findOne(example);
	}
	
}
