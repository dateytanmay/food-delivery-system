## Food Delivery Backend Service

## Overview
This project is a scalable backend service for processing food delivery orders using Java, Spring Boot, MySQL, and an in-memory queue (simulating AWS SQS).

---

## Setup Instructions

### 1. Clone the repository
```bash
git clone https://github.com/dateytanmay/food-delivery-system.git
cd food-delivery-backend
2. Start MySQL
If using Docker:

bash
Copy code
docker run --name mysql_container -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=food_delivery -p 3306:3306 -d mysql:latest
3. Configure application.properties
properties
Copy code
spring.datasource.url=jdbc:mysql://localhost:3306/food_delivery
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
4. Run the application
bash
Copy code
mvn spring-boot:run
Database Schema
SQL script (src/main/resources/schema.sql):

sql
Copy code
CREATE DATABASE IF NOT EXISTS food_delivery;

USE food_delivery;

CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL,
    items TEXT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    order_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) DEFAULT 'PENDING'
);
API Endpoints
1. Place an Order
POST /api/orders

json
Copy code
{
  "customerName": "John Doe",
  "items": "Pizza, Coke",
  "totalAmount": 299.99
}
cURL

bash
Copy code
curl -X POST http://localhost:8080/api/orders \
   -H "Content-Type: application/json" \
   -d '{"customerName": "John Doe", "items": "Pizza, Coke", "totalAmount": 299.99}'
2. Get All Orders (with Pagination)
GET /api/orders?page=0&size=10

cURL

bash
Copy code
curl "http://localhost:8080/api/orders?page=0&size=10"
3. Get Order Status
GET /api/orders/{id}

cURL

bash
Copy code
curl "http://localhost:8080/api/orders/1"
4. Update Order Status Manually
PUT /api/orders/{id}/status?status=DELIVERED

cURL

bash
Copy code
curl -X PUT "http://localhost:8080/api/orders/1/status?status=DELIVERED"
Queue Testing Instructions
Place an order using POST /api/orders.

The order is added to an in-memory queue with status "PENDING".

The background scheduler (OrderProcessor) runs every 5 seconds.

It polls the queue and updates the order status to "PROCESSED".

Verify the order status using GET /api/orders/{id}.

You should see "PROCESSED" after a few seconds.

You can manually override the status using PUT /api/orders/{id}/status?status=DELIVERED.

Sample Response
Place Order Response

json
Copy code
{
  "id": 1,
  "customerName": "John Doe",
  "items": "Pizza, Coke",
  "totalAmount": 299.99,
  "orderTime": "2025-09-10T12:34:56",
  "status": "PENDING"
}
Get Orders Response

json
Copy code
{
  "content": [
    {
      "id": 1,
      "customerName": "John Doe",
      "items": "Pizza, Coke",
      "totalAmount": 299.99,
      "orderTime": "2025-09-10T12:34:56",
      "status": "PROCESSED"
    }
  ],
  "pageable": {...},
  "totalElements": 1,
  "totalPages": 1
}
Notes
Uses DTOs and service layer for best practices.

Simulated queue via ConcurrentLinkedQueue for asynchronous processing.

Database changes auto-committed via JPA/Hibernate.

yaml
Copy code

---

## 3️⃣ **ZIP / GitHub Submission**

1. Commit all code, README, and `schema.sql`.
2. Push to GitHub:
```bash
git init
git add .
git commit -m "Pre-interview assignment submission"
git branch -M main
git remote add origin https://github.com/your-username/food-delivery-backend.git
git push -u origin main
Or zip the folder:

bash
Copy code
zip -r food-delivery-backend.zip food-delivery-backend/
If you want, I can prepare a ready-to-submit ZIP with all files, proper folder structure, and working code so you can submit immediately.

Do you want me to do that?







You said:
create readme file for all this


ChatGPT said:
Sure! Here’s a complete, submission-ready README.md file for your backend assignment, covering setup, endpoints, sample payloads, queue testing, and database schema. You can directly add it to your project.

markdown
Copy code
# Food Delivery Backend Service

## Overview
This project is a scalable backend service for processing food delivery orders using **Java**, **Spring Boot**, **MySQL**, and an **in-memory queue** simulating AWS SQS.  
It supports placing orders, fetching orders with pagination, asynchronous processing, and updating order status.

---

## Project Structure

food-delivery-backend/
├── src/
│ ├── main/
│ │ ├── java/com/example/fooddelivery/
│ │ │ ├── controller/
│ │ │ ├── dto/
│ │ │ ├── entity/
│ │ │ ├── repository/
│ │ │ ├── service/
│ │ │ ├── queue/
│ │ │ └── FoodDeliveryApplication.java
│ │ └── resources/
│ │ ├── application.properties
│ │ └── schema.sql
├── pom.xml
├── README.md
└── .gitignore

yaml
Copy code

---

## Setup Instructions

### 1. Clone the repository

```bash
git clone https://github.com/your-username/food-delivery-backend.git
cd food-delivery-backend
2. Start MySQL
If using Docker:

bash
Copy code
docker run --name mysql_container -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=food_delivery -p 3306:3306 -d mysql:latest
3. Configure application.properties
properties
Copy code
spring.datasource.url=jdbc:mysql://localhost:3306/food_delivery
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
4. Run the application
bash
Copy code
mvn spring-boot:run
Database Schema
src/main/resources/schema.sql:

sql
Copy code
CREATE DATABASE IF NOT EXISTS food_delivery;

USE food_delivery;

CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL,
    items TEXT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    order_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) DEFAULT 'PENDING'
);
API Endpoints
1. Place an Order
POST /api/orders

Request Body

json
Copy code
{
  "customerName": "John Doe",
  "items": "Pizza, Coke",
  "totalAmount": 299.99
}
cURL

bash
Copy code
curl -X POST http://localhost:8080/api/orders \
   -H "Content-Type: application/json" \
   -d '{"customerName": "John Doe", "items": "Pizza, Coke", "totalAmount": 299.99}'
2. Get All Orders (with Pagination)
GET /api/orders?page=0&size=10

cURL

bash
Copy code
curl "http://localhost:8080/api/orders?page=0&size=10"
3. Get Order Status by ID
GET /api/orders/{id}

cURL

bash
Copy code
curl "http://localhost:8080/api/orders/1"
4. Update Order Status Manually
PUT /api/orders/{id}/status?status=DELIVERED

cURL

bash
Copy code
curl -X PUT "http://localhost:8080/api/orders/1/status?status=DELIVERED"
Queue Testing Instructions (Simulated)
Place an order using POST /api/orders.

The order is added to an in-memory queue with status "PENDING".

The background scheduler (OrderProcessor) runs every 5 seconds.

It polls the queue and updates the order status to "PROCESSED" automatically.

Fetch the order using GET /api/orders/{id}.

The status should change to "PROCESSED" after a few seconds.

Optionally, manually override the status using PUT /api/orders/{id}/status?status=DELIVERED.

Sample API Responses
Place Order Response

json
Copy code
{
  "id": 1,
  "customerName": "John Doe",
  "items": "Pizza, Coke",
  "totalAmount": 299.99,
  "orderTime": "2025-09-10T12:34:56",
  "status": "PENDING"
}
Get Orders Response

json
Copy code
{
  "content": [
    {
      "id": 1,
      "customerName": "John Doe",
      "items": "Pizza, Coke",
      "totalAmount": 299.99,
      "orderTime": "2025-09-10T12:34:56",
      "status": "PROCESSED"
    }
  ],
  "pageable": {...},
  "totalElements": 1,
  "totalPages": 1
}