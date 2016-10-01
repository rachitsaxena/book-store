package com.rachit.bookstore.service.book.messaging;

public class MasterSyncBookDetails {

	/** Book ID from Master db*/
	private Long masterBookId;
	
	/**Mongo Book ID*/
	private String mongoBookId;
	
	public MasterSyncBookDetails() {
	}
	
	public MasterSyncBookDetails(Long masterBookId, String mongoBookId) {
		this.masterBookId = masterBookId;
		this.mongoBookId = mongoBookId;
	}
	
	public Long getMasterBookId() {
		return masterBookId;
	}
	
	public void setMasterBookId(Long masterBookId) {
		this.masterBookId = masterBookId;
	}
	
	public String getMongoBookId() {
		return mongoBookId;
	}
	
	public void setMongoBookId(String mongoBookId) {
		this.mongoBookId = mongoBookId;
	}
}
