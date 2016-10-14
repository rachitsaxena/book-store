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
  - Hystrix - Hystrix and Hystrix Dashboard
  - Spring cloud Stream - Even Driven Microservices with Kafka messaging.
  - Spring Sleuth - Enable Trace and Span IDs for Centralised Logging
- Centralised Logging (12-Factor requirement) using ELK (ElasticSearch, Logstash and Kibana)
- RabbitMQ
- MongoDB 
- H2
- More to be added 

## Before starting services, start following components:
- Zookeeper
- Kafka
- RabbitMQ
- MongoDB
- Logstash
- ElasticSearch
- Kibana

## Port Information
- search-service : 8005
- order-service : 8011
- book-service : 8101
- inventory-service : 9501
- profile-service : 9010
- hystrix-dashboard : 9020

## To run these applications
- Following entries are added to /etc/hosts. These entries are required when running services on local. Turnbine has limitation of one hostname per service.
  - 127.0.0.1       bookservicelocal
  - 127.0.0.1       inventoryservicelocal
  - 127.0.0.1       profileservicelocal
  - 127.0.0.1       orderservicelocal
  - 127.0.0.1       searchservicelocal


## Notes
- RabbitMQ configuration file path: /usr/local/etc/rabbitmq/rabbitmq.config
