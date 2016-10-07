package com.rachit.bookstore.service.search;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
public class MongoDBConfiguration extends AbstractMongoConfiguration {

	@Value("${spring.data.mongodb.host}")
	private String mongoHost;
	@Value("${spring.data.mongodb.port}")
	private int mongoPort;
	@Value("${spring.data.mongodb.database}")
	private String booksDatabaseName;
	
	@Override
	protected String getDatabaseName() {
		return booksDatabaseName;
	}

	@Override
	public Mongo mongo() throws Exception {
		return new MongoClient(mongoHost, mongoPort);
	}
	
	@Override
	protected String getMappingBasePackage() {
		return "com.rachit.bookstore.service.search";
	}

}
