package com.rachit.bookstore.service.order.proxy;

import java.util.UUID;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rachit.bookstore.service.book.entity.Book;

@FeignClient(name = "book-service") 
@RibbonClient
public interface BookServiceProxy {

	@RequestMapping(value = "/books/isbn/{isbn}", method = RequestMethod.GET)
	public Book findBookByIsbn(@PathVariable("isbn") UUID isbn);
	
	
}
