# E-Commerce Order Management API

[![Java CI with Maven](https://github.com/jarzeckil/ecommerce-api/actions/workflows/maven.yml/badge.svg)](https://github.com/jarzeckil/ecommerce-api/actions/workflows/maven.yml)

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED?style=for-the-badge&logo=docker&logoColor=white)

A robust, transactional RESTful API designed to handle e-commerce operations, focusing on product catalog management and secure order processing.

This project demonstrates a clean architecture approach using **Java** and **Spring Boot**, with a strong emphasis on data integrity, ACID transactions, and proper handling of Many-to-Many relationships with historical attributes.

## 🚀 Key Features

### 🐳 Containerization & Infrastructure (Docker First)
* **Fully Containerized Ecosystem:** The entire application stack (API + PostgreSQL Database) is orchestrated via **Docker Compose**.
* **One-Command Setup:** Eliminates "works on my machine" issues. A single `docker-compose up` command builds the Java application (using multi-stage Docker builds) and provisions the database, making the project strictly **production-ready**.
* **Isolated Environment:** The application runs in a lightweight, consistent Linux-based container environment, independent of the host OS configuration.

### ⚙️ Core Business Logic
* **Transactional Order Processing:** Implements atomic operations using `@Transactional` to ensure that orders and their line items are persisted together or rolled back entirely in case of failure.
* **Historical Price Freezing:** Solves the classic e-commerce problem of changing catalog prices. The system snapshots the price of an item at the moment of purchase, preserving the order history integrity.
* **Advanced ORM:** Utilizes Hibernate/JPA for complex entity mapping, including `OneToMany` and bidirectional relationships.

### 🛡️ Quality Assurance & Security
* **Robust Input Validation:** Utilizes `Jakarta Validation` to protect the API from invalid IDs, negative quantities, or malformed requests before they reach business logic.
* **Centralized Exception Handling:** Implements `@RestControllerAdvice` to transform uncaught exceptions into standardized, user-friendly JSON error responses.
* **Automated CI Pipeline:** Integrated **GitHub Actions** workflow that automatically builds the Docker image and runs unit tests on every push, ensuring code quality and build stability.
* **Interactive Documentation:** Integrated **Swagger/OpenAPI** (accessible via the containerized app) for real-time API exploration and testing.

## 🛠️ Tech Stack

* **Language:** Java 21
* **Framework:** Spring Boot 3 (Spring Web, Spring Data JPA, Validation)
* **Database:** PostgreSQL 15 (Production/Dev), H2 (Testing)
* **Testing:** JUnit 5, Mockito
* **DevOps:** Docker & Docker Compose, GitHub Actions
* **Tools:** Maven, Lombok, Swagger UI

## 🏗️ Architecture & Database Model

The system is built around a normalized relational database schema consisting of 5 core entities:

1.  **Customer:** User identity management.
2.  **ItemCategory:** Product classification.
3.  **Item:** The product catalog (contains current prices).
4.  **Order:** Order header (timestamp, customer reference).
5.  **ItemOrder (Join Table):** The association entity linking Orders and Items. It stores the *quantity* and the *frozen price* at the transaction time.

## 📦 Getting Started

### Prerequisites
* **Recommended:** Docker & Docker Compose (That's it! Java/Maven are NOT required for the Docker setup).
* **For Local Dev:** Java JDK 21 and Maven (Only if you want to run the app outside Docker).

### 🐳 Option 1: Quick Start (Docker - Recommended)
The entire environment (Application + Database) is containerized. This ensures the app runs exactly as intended, regardless of your local OS.

1.  **Clone the repository**
    ```bash
    git clone [https://github.com/jarzeckil/ecommerce-api.git](https://github.com/jarzeckil/ecommerce-api.git)
    cd ecommerce-api
    ```

2.  **Run with Docker Compose**
    This command compiles the code, builds the Docker image, and starts both the API and the Database.
    ```bash
    docker-compose up --build
    ```
    *Wait until you see `Started MtabApiApplication` in the logs.*

3.  **Access the Application**
    * **Frontend UI:** [http://localhost:8080](http://localhost:8080)
    * **Swagger Docs:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
    * **Database:** Port `5432` is exposed for local tools (User/Pass: `postgres`/`postgres`).

---

### 🔧 Option 2: Local Development (Hybrid)
Use this method if you want to debug the Java code in your IDE while keeping the database in Docker.

1.  **Start only the Database**
    ```bash
    docker-compose up postgres-db -d
    ```

2.  **Run the App locally**
    ```bash
    ./mvnw spring-boot:run
    ```

## 🖥️ API Dashboard (Basic UI)

The application includes a lightweight frontend client for testing API endpoints directly in the browser, eliminating the need for external tools like Postman for basic flows.

- **URL:** [http://localhost:8080](http://localhost:8080)
- **Source:** `src/main/resources/static/index.html`

### Features
* **📦 Items Browser:** View all items, filter by `Category ID`, or lookup by `Item ID`. Data is presented in formatted tables instead of raw JSON.
* **🛒 Order Placement:** A user-friendly form to create orders. Includes dynamic fields for adding/removing items and inputs for `Client ID` and quantity.
* **🚦 Visual Feedback:** Handles HTTP responses gracefully, displaying success messages or color-coded error alerts (e.g., 400 Bad Request validation errors, 404 Not Found).

The server will start on port `8080`.

*Note: The application includes a `DataSeeder` that will automatically populate the database with sample products and customers upon the first launch.*

## 🔌 API Documentation

Once the application is running, you can access the interactive Swagger UI to test endpoints:

👉 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

### Main Endpoints

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/items` | Retrieve all products |
| `GET` | `/items?categoryId={id}` | Filter products by category |
| `GET` | `/items/{id}` | Get product details |
| `POST` | `/orders` | Place a new order (Transactional) |

### Sample JSON for Order Placement (POST)

```json
{
  "customerId": 1,
  "items": [
    {
      "itemId": 1,
      "quantity": 2
    },
    {
      "itemId": 3,
      "quantity": 1
    }
  ]
}
```
## 🧪 Testing

### Unit Tests
The project includes a suite of unit tests focusing on the `OrderService`. These tests use **Mockito** to mock Repositories, allowing for fast verification of business logic (e.g., price freezing, customer validation) without spinning up the full Spring Context.

To run the unit tests, execute:
```bash
./mvnw test
```
### Integration Tests
End-to-end integration tests are implemented using `@SpringBootTest` and an in-memory **H2 database**. These tests verify the entire request lifecycle—from the REST Controller, through the Service layer, down to the Database persistence—ensuring all components interact correctly without requiring a running PostgreSQL container.

**Key scenarios covered:**
* **Happy Path:** Placing a valid order via `MockMvc` and verifying it persists in the H2 database.
* **Error Handling:** Verifying that invalid input (e.g., negative quantity) returns `400 Bad Request` and proper JSON error messages.
* **Data Integrity:** Ensuring `@Transactional` works correctly and rolls back data between tests to maintain a clean state.


## 💡 Learning Outcomes

Building this project provided deep practical insights into modern backend development:

* **Spring Context & IoC:** Gained a solid understanding of Dependency Injection and how Spring manages bean lifecycles.
* **Database Transactions:** Learned how `@Transactional` proxies work to guarantee data consistency (ACID) across multiple tables, specifically implementing the **historical price freezing** logic.
* **DTO Pattern:** Understood the importance of separating database Entities from Data Transfer Objects (DTOs) for security and API versioning.
* **Input Validation & Exception Handling:** Learned how to sanitize user input using Jakarta Validation and implement a global exception handler (`@ControllerAdvice`) for clean, standardized API error responses.
* **Docker & Infrastructure:** Learned to define Infrastructure as Code (IaC) using **Docker Compose** (including multi-stage builds) to create reproducible, production-ready environments independent of the host OS.
* **API Documentation:** Integrated **OpenAPI (Swagger)** to automatically generate interactive documentation, improving the developer experience and testing workflow.
* **Full-Stack Integration:** Built a lightweight frontend client using vanilla **JavaScript (Fetch API)** to consume REST endpoints, gaining insight into CORS and JSON serialization/deserialization.
* **CI/CD & Testing:** Configured a **GitHub Actions** pipeline to automate the build and test process, enforcing code quality standards on every commit.
* **Problem Solving:** Solved real-world compatibility issues ("Dependency Hell") between Spring Boot versions and third-party libraries by analyzing stack traces and managing Maven configurations.
