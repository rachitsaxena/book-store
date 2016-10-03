# Book Store Application 

Book Store is an application developed as a POC using Spring boot based microservices. This application has following fetatures:

- Register Buyers and Sellers
- Create Master records for Books
- Maintain Inventory of books from different sellers
- Maintain a consolidated view in NoSQL (MongoDB) to search books and see their availibility by sellers.
- Place orders for a selected book.


The aim of this application is not to provide complete working solution as online retail stores. Instead the aim of cover different features offered by Spring Boot and Spring Cloud. The tech stack used to develop these microservices are as following:

- Spring Boot
- Spring Cloud (Eureka, Cloud Config server, API Gateway (Zuul), Load Balancer
- RabbitMQ
- MongoDB
- H2
- More to be added 


# Port Information
- search-service : 8005
- book-service : 8080
- inventory-service : 9501
- profile-service : 9010
- order-service : 8011


This POC is still a work in progress and will be completed soon..

