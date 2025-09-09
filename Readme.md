## Food Delivery Backend Service

## Overview
This project is a scalable backend service for processing food delivery orders using Java, Spring Boot, MySQL, and an in-memory queue (simulating AWS SQS).

---

## Setup Instructions

### 1. Clone the repository

`git clone https://github.com/dateytanmay/food-delivery-system.git`
`cd food-delivery-backend`

### 2. Start MySQL
If using Docker:

`docker run --name mysql_container -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=food_delivery -p 3306:3306 -d mysql:latest`


### 3. Configure application.properties

spring.datasource.url=jdbc:mysql://localhost:3306/food_delivery
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

###4. Run the application

`mvn spring-boot:run`
## SQL Schema

`CREATE DATABASE IF NOT EXISTS food_delivery;
USE food_delivery;
CREATE TABLE orders (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
customer_name VARCHAR(255) NOT NULL,
items TEXT NOT NULL,
total_amount DECIMAL(10,2) NOT NULL,
order_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
status VARCHAR(50) DEFAULT 'PENDING'
);`


## API Endpoints
### 1. Place an Order

### POST /api/orders

{
  "customerName": "John Doe",
  "items": "Pizza, Coke",
  "totalAmount": 299.99
}

`curl -X POST http://localhost:8080/api/orders \
   -H "Content-Type: application/json" \
   -d '{"customerName": "John Doe", "items": "Pizza, Coke", "totalAmount": 299.99}'`

### 2.Get All Orders (with Pagination)
GET /api/orders?page=0&size=10

`curl "http://localhost:8080/api/orders?page=0&size=10"`

### 3.Get Order Status
GET /api/orders/{id}


`curl "http://localhost:8080/api/orders/1"`

### 4. Update Order Status Manually
PUT /api/orders/{id}/status?status=DELIVERED


`curl -X PUT "http://localhost:8080/api/orders/1/status?status=DELIVERED"`

---
## Queue Testing Instructions
Place an order using POST /api/orders.

The order is added to an in-memory queue with status "PENDING".

The background scheduler (OrderProcessor) runs every 5 seconds.

It polls the queue and updates the order status to "PROCESSED".

Verify the order status using GET /api/orders/{id}.

You should see "PROCESSED" after a few seconds.

You can manually override the status using PUT /api/orders/{id}/status?status=DELIVERED.

