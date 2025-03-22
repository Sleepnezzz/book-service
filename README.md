# 📚 Book Management API

This is a simple RESTful API built with Spring Boot and Kotlin (or Java) for managing books in a MySQL database.

## ✨ Features
- 📖 Fetch books by author
- ✍️ Save a new book with validation
- 🛠️ Integration tests for API validation

## 🛠️ Technologies Used
- 🌱 Spring Boot
- 💻 Kotlin/Java
- 🛢️ MySQL
- ⚡ Hibernate
- 📊 Spring Data JPA

---

## 🔧 Setup and Installation

### 📌 Prerequisites
- 🐳 Docker (optional, for running MySQL locally)
- ☕ Java 17+
- 📦 Gradle

### 🗄️ How to Setup

#### Using docker run
```sh
# Mysql Database
docker run --name=book_db --network backend -e MYSQL_ROOT_PASSWORD=book@1234 -e MYSQL_DATABASE=book_db -p 3306:3306 -d mysql

# phpmyadmin
docker run --name phpmyadmin --network backend -p 8081:80 -e PMA_HOST=book_db -e PMA_PORT=3306 -e PMA_ARBITRARY=1 -d phpmyadmin/phpmyadmin:latest

# Build & Running the book_service
docker build -t book_service .
docker run --name book_service --network backend -p 8080:8080 -e MYSQL_HOST=book_db -d book_service

# Note: need create docker network
docker network create backend
```

#### 🏗️ Or Using Docker compose:
```sh
docker-compose up -d
```

---
#### 📖 API Specific Endpoints
🔍 Open Swagger UI: http://localhost:8080/swagger-ui.html

---
#### 🧪 Running Integration Tests
using Gradle: 
```
./gradlew test
```

---
#### 📜 License
- This project is open-source and free to use.