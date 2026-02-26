# 🛒 Backend Microservices 

## 📌 Overview
This project is a Retail ordering website Microservices architecture built using Spring Boot, Spring Security, JWT Authentication, Eureka Service Discovery, Spring Cloud Gateway, MySQL, and Maven. 

The system is divided into independent microservices:
- Eureka Discovery Service
- API Gateway
- Authentication Service
- Buyer Service
- Seller Service

Each service is independently deployable and follows industry-standard microservices architecture.

---

## 🏗 Architecture

Client (Frontend)
        ↓
API Gateway (Port 8080)
        ↓
---------------------------------
| Auth | Buyer | Seller |
---------------------------------
        ↓
MySQL Database (Database per Service)

---

## 🔧 Microservices Details

### 1️⃣ Eureka Discovery Service
Port: 8761  
Purpose:
- Registers all services
- Maintains service registry
- Enables service discovery and load balancing

Dashboard:
http://localhost:8761

---

### 2️⃣ API Gateway
Port: 8080  
Purpose:
- Single entry point for all requests
- Routes requests to appropriate services
- Validates JWT tokens
- Handles CORS
- Centralized security filtering

Routing:
- /api/auth/**   → AUTH-SERVICE
- /api/buyer/**  → BUYER-SERVICE
- /api/seller/** → SELLER-SERVICE

---

### 3️⃣ Authentication Service
Port: 8081  
Database: auth_db  

Responsibilities:
- User Registration
- User Login
- Generate JWT Token
- Validate JWT Token
- Role-based access control (USER, SELLER)

Endpoints:
- POST /api/auth/register
- POST /api/auth/login
- GET  /api/auth/validate

---

### 4️⃣ Buyer Service
Port: 8082  
Database: buyer_db  

Responsibilities:
- View products
- Add to cart
- Place orders
- View order history

Protected Endpoints (JWT required):
- GET  /api/buyer/products
- POST /api/buyer/cart/add
- POST /api/buyer/order

---

### 5️⃣ Seller Service
Port: 8083  
Database: seller_db  

Responsibilities:
- Add product
- Update product
- Delete product
- Manage inventory

Protected Endpoints (JWT required):
- POST   /api/seller/product
- PUT    /api/seller/product/{id}
- DELETE /api/seller/product/{id}

---

## 🔐 Authentication Flow (JWT)

1. Client sends login request to /api/auth/login
2. Auth Service validates credentials
3. JWT token is generated and returned
4. Client sends Authorization: Bearer <token> in future requests
5. API Gateway validates token
6. If valid → forwards to Buyer/Seller service
7. If invalid → returns 401 Unauthorized

---

## 🛠 Tech Stack

Backend Framework: Spring Boot  
Security: Spring Security + JWT  
Service Discovery: Eureka  
Gateway: Spring Cloud Gateway  
Database: MySQL  
Build Tool: Maven  
Testing: JUnit + Mockito  

---

## 📂 Project Structure

ecommerce-microservices/
│
├── discovery-service/
├── api-gateway/
├── auth-service/
├── buyer-service/
└── seller-service/

Each service structure:

src/main/java
 ├── controller/
 ├── service/
 ├── repository/
 ├── entity/
 ├── config/
 └── security/

---

## 🚀 How to Run

1. Start discovery-service (Port 8761)
2. Start auth-service (Port 8081)
3. Start buyer-service (Port 8082)
4. Start seller-service (Port 8083)
5. Start api-gateway (Port 8080)

Access system via:
http://localhost:8080
