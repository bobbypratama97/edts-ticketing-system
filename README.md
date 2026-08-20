# EDTS Concert Ticketing System

High-concurrency RESTful API for concert ticket booking built with Spring Boot 3, Spring Data JPA, and H2 In-Memory Database. Designed to handle high-volume ticket sales without race conditions or quota overbooking.

---

## Tech Stack

* Java: 17
* Framework: Spring Boot 3.x
* Database: H2 In-Memory Database
* ORM: Spring Data JPA / Hibernate
* Documentation: OpenAPI 3.0 / Swagger UI
* Build Tool: Maven

---

## Key Features

* Concert Search API: Search concerts by keyword (name or artist).
* Ticket Booking API: Real-time ticket reservation powered by Pessimistic Locking (@Lock(LockModeType.PESSIMISTIC_WRITE)) to prevent race conditions and overbooking under heavy concurrent traffic.
* Booking Retrieval API: Track all processed booking transactions.
* Interactive API Documentation: Test all REST endpoints visually via Swagger UI.

---

## Getting Started

### Prerequisites
* Java 17 or higher
* Maven 3.8+ (or use ./mvnw wrapper)

### Running the Application

1. Clone the repository:
   git clone <repository-url>
   cd edts-ticketing-system

2. Run the Spring Boot application:
   ./mvnw spring-boot:run

   (The application will automatically start on http://localhost:8080 and seed dummy concert data)

---

## API Documentation & Testing

Once the application is running, access the interactive Swagger UI at:
http://localhost:8080/swagger-ui.html

Available Endpoints:
* GET /api/concerts?keyword={keyword} - Search concerts and view real-time ticket category quotas.
* POST /api/bookings - Book tickets for a specific category.
* GET /api/bookings - Retrieve all completed booking records.

---

## Running Unit & Concurrency Tests

To execute the test suite (including the concurrent thread execution test for pessimistic locking):
./mvnw test