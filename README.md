
# 📌 Spring Boot Task API

A **RESTful API** for user authentication and task management, built with **Spring Boot** for a take-home assignment.

**Key Features:**  
- JWT-based authentication & authorization  
- BCrypt password hashing for secure password storage  
- H2 in-memory database for quick setup and testing  
- Layered architecture: Controller → Service → Repository  
- Global exception handling with `@ControllerAdvice`  
- Fully documented endpoints with curl/Postman examples  

---

## 🔧 Technology Stack
- **Java 17**  
- **Spring Boot 3.x**  
- **Spring Security (JWT)**  
- **Spring Data JPA (H2 Database)**  
- **Maven**  
- **JUnit** (optional, for unit testing)

---

## 🚀 Quick Start

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/<your-username>/springboot-task-api.git
cd springboot-task-api
````

### 2️⃣ Build & Run

```bash
# Using Maven Wrapper
./mvnw clean package
./mvnw spring-boot:run

# Or with global Maven
mvn clean package
mvn spring-boot:run
```

**Server URL:** `http://localhost:8080`
**H2 Console:** `http://localhost:8080/h2-console`
JDBC URL: `jdbc:h2:mem:testdb` | User: `sa` | Password: (blank)

---

## 🔐 Authentication Flow

1. **Register User**:
   `POST /auth/register` with `{ email, password, name }`

   * Passwords hashed using BCrypt
   * Returns JWT token

2. **Login User**:
   `POST /auth/login` with `{ email, password }`

   * Returns `{ "accessToken": "<JWT>" }`

3. **Protected Endpoints**:
   Send header `Authorization: Bearer <JWT>` to access `/tasks/*`.

---

## 📚 API Endpoints

### Authentication

| Method | Endpoint         | Description                              |
| ------ | ---------------- | ---------------------------------------- |
| POST   | `/auth/register` | Register new user                        |
| POST   | `/auth/login`    | Authenticate & receive JWT               |
| POST   | `/auth/logout`   | Stateless logout (client discards token) |

### Tasks (Requires Authorization)

| Method | Endpoint      | Description                           |
| ------ | ------------- | ------------------------------------- |
| POST   | `/tasks`      | Create a new task                     |
| GET    | `/tasks`      | Get all tasks for logged-in user      |
| PUT    | `/tasks/{id}` | Update task status                    |
| DELETE | `/tasks/{id}` | Delete a task (only owner can delete) |

---

## ✅ Expected HTTP Status Codes

* **200 OK** – Successful GET/PUT/POST operations
* **201 Created** – Resource successfully created
* **400 Bad Request** – Invalid inputs or login failure
* **401 Unauthorized** – No or invalid token for protected endpoints
* **403 Forbidden** – Accessing another user’s resource
* **404 Not Found** – Resource or task not found

---

## 🧪 Testing

Run unit tests (if included):

```bash
./mvnw test
```

---

## 🧭 Project Structure

```
src/main/java/com/example/taskapi/
 ├─ config/        # Security, JWT utilities, filters
 ├─ controller/    # REST controllers
 ├─ service/       # Business logic
 ├─ repository/    # JPA repositories
 ├─ entity/        # JPA entities (User, Task)
 ├─ dto/           # Request/response DTOs (optional)
 └─ exception/     # Global exception handling (@ControllerAdvice)
```

---

## 🔁 Postman Collection

* Included as `SpringBoot-Task-API.postman_collection.json`
* Contains all endpoints with pre-configured headers and sample payloads
* Import into Postman and set `jwtToken` variable after login to test protected routes

---

## 🔗 Example `curl` Requests

**Register**

```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"mypassword","name":"John Doe"}'
```

**Login**

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"mypassword"}'
```

**Create Task** (requires token)

```bash
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{"title":"Buy milk","description":"From store","status":"OPEN"}'
```

---

## ✅ Submission Checklist

* [x] All endpoints implemented & tested
* [x] Passwords stored hashed (BCrypt)
* [x] JWT authentication required for `/tasks`
* [x] Global exception handling present
* [x] README with run instructions & examples
* [x] Public GitHub repo with clean commits

---

**Author / Contact:**
Sanjeev Kumar Ray — [GitHub Profile](https://github.com/sanjeevkumarray)



