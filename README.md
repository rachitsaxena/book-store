# Book Store Application 

Book Store is an application developed as a POC using Spring boot based microservices. This application has following fetatures:

- Register Buyers and Sellers
- Create Master records for Books
- Maintain Inventory of books from different sellers
- Maintain a consolidated view in NoSQL (MongoDB) to search books and see their availibility by sellers.
- Place orders for a selected book.


The aim of this application is not to provide complete working solution as online retail stores. Instead the aim is to cover different features offered by Spring Boot and Spring Cloud. The tech stack used to develop these microservices are as following:

- Spring Boot
- Spring Cloud 
  - Eureka - Service Registry and Discovery
  - Cloud Config server - Externalizing Service Configurations
  - Ribbon - Client side Load Balancer
  - Fiegn - Proxy interfaces for remote calls.
  - Spring cloud Stream - Even Driven Microservices with Kafka messaging.
  - Spring Sleuth - Enable Trace and Span IDs for Centralised Logging
- Centralised Logging (12-Factor requirement) using ELK (ElasticSearch, Logstash and Kibana)
- RabbitMQ
- MongoDB 
- H2
- More to be added 


## Port Information
- search-service : 8005
- book-service : 8080
- inventory-service : 9501
- profile-service : 9010
- order-service : 8011


This POC is still a work in progress and will be completed soon..

