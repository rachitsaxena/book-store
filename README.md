# Book Store Application 

Book Store is an application developed as a POC using Spring boot based microservices. This application has following fetatures:

- Register Buyers and Sellers
- Create Master records for Books
- Maintain Inventory of books from different sellers
- Maintain a consolidated view in NoSQL (MongoDB) to search books and see their availibility by sellers.
- Place orders for a selected book.

At high level, the design of application looks as below. As this is just a POC, all the services may not be functional (For example, review-serivce, loyalty-service, logistic-service and notification-service). If time permits, these services also will be completed.

  ![Alt text](/design-documents/HLD.png?raw=true "Optional Title")

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

# Setup
## Create host entries
- Following entries are added to /etc/hosts. These entries are required when running services on local. Turnbine has limitation of one hostname per service.
  - 127.0.0.1       bookservicelocal
  - 127.0.0.1       inventoryservicelocal
  - 127.0.0.1       profileservicelocal
  - 127.0.0.1       orderservicelocal
  - 127.0.0.1       searchservicelocal

## Before starting services, start following components:
- Zookeeper
  - ./bin/zookeeper-server-start.sh config/zookeeper.properties
- Kafka
  - ./bin/kafka-server-start.sh config/server.properties
- RabbitMQ
  - sudo rabbitmq-server (Copy config file from supporting-files/rabbitmq.config to /usr/local/etc/rabbitmq/rabbitmq.config)
- MongoDB
  - sudo ./bin/mongod
- ElasticSearch
  - ./bin/elasticsearch
- Logstash
  - ./bin/logstash -f logstash.conf
- Kibana
  - ./bin/kibana

## Port Information
- search-service : 8005
- order-service : 8011
- book-service : 8101
- inventory-service : 9501
- profile-service : 9010
- hystrix-dashboard : 9020

## starting the services
- Start Config-server
  - Go to directory book-store/config-server and hit 
    ```
     mvn spring-boot:run
    ```
- Start Eureka-server
  - Go to directory book-store/eureka-server and hit 
    ```
     mvn spring-boot:run
    ```
- Start microservices
  - Find IP of local linux box (let's say its 192.168.0.102).
  - Update IP addresses in all bootstrap.properites (<xxx-service>/<xxx-service-jar>/src/main/resources/bootstrap.properites) for property: 
  ```
  spring.cloud.config.uri=http://192.168.0.102:8888
  ```
  - In the same way,
    - clone this repository (https://github.com/rachitsaxena/book-store-cloud-configs) 
    - create a local git repository
    - Copy all the files to local git repository and edit following property 
      ```
      spring.rabbitmq.host=192.168.0.102
      
      spring.rabbitmq.port=5672
      
      spring.rabbitmq.username=test
      
      spring.rabbitmq.password=test
      
      eureka.client.serviceUrl.defaultZone=http://192.168.0.102:8761/eureka
      ```
    - Commit all *-service.properites files to local git reposiotry.
  - With these configuration changes, we are done with pointing microservices to config-server and eureka-server running locally without docker. Actually, these properties should be parameterized. I will make these properties configurable shortly.
  - Next step is to start the microservices. Start docker daemon process.
  - Go to book-store directory and hit following command:
    ```
    mvn clean install
    ```
  - Got to individual services directories and hit the following command in respective directoires to build docker images:
    ```
    docker build –t book-service:1.0 .
    
    docker build –t inventory-service:1.0 .
    
    docker build –t order-service:1.0 .
    
    docker build –t profile-service:1.0 .
    
    docker build –t search-service:1.0 .
    ```  

  - Check if all the images are created with command 'docker images’
  - Run the images to start the services:
    ```
    docker run --net host -p 9010:9010 -t profile-service:1.0
    
    docker run --net host -p 8005:8005 -t search-service:1.0
    
    docker run --net host -p 8011:8011 -t order-service:1.0
    
    docker run --net host -p 8101:8101 -t book-service:1.0
    
    docker run --net host -p 9501:9501 -t inventory-service:1.0
    ```
  - This should start and register all the services with eureka.
  - Once services are started successfully, the eureka server console will look like this:

  ![Alt text](/supporting-files/screenshots/Eureka-Services-Running.png?raw=true "Optional Title")
