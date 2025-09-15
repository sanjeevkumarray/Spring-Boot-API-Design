

---

````markdown
# 📌 SpringBoot Task API

A small **Spring Boot REST API** for user authentication and task management — built for a take-home assignment.

**Features:**  
✅ JWT-based authentication  
✅ BCrypt password hashing  
✅ H2 in-memory DB  
✅ Layered architecture (Controller → Service → Repository)  
✅ Global exception handling with `@ControllerAdvice`  

---

## 🔧 Tech Stack

- **Java 17**
- **Spring Boot 3.x**
- **Spring Security (JWT)**
- **Spring Data JPA (H2)**
- **Maven**
- **JUnit** (for tests)

---

## 🚀 Quick Start

### 1️⃣ Clone the project
```bash
git clone https://github.com/<your-username>/springboot-task-api.git
cd springboot-task-api
````

### 2️⃣ Build & Run

```bash
./mvnw clean package
./mvnw spring-boot:run
```

Or with global Maven:

```bash
mvn clean package
mvn spring-boot:run
```

App runs at: **[http://localhost:8080](http://localhost:8080)**
H2 console: **[http://localhost:8080/h2-console](http://localhost:8080/h2-console)**
(JDBC URL: `jdbc:h2:mem:testdb`, user: `sa`, password: *blank*)

---

## 🔐 Authentication Flow (High-Level)

* **Register user:** `POST /auth/register` with `{ email, password, name }`
  → Password is stored hashed with BCrypt → Returns JWT token
* **Login user:** `POST /auth/login` with `{ email, password }`
  → Returns `{ "accessToken": "<JWT>" }`
* **Protect endpoints:** Send header `Authorization: Bearer <JWT>` to access `/tasks/*`

---

## 📚 API Endpoints

### Auth

| Method | Endpoint         | Description                              |
| ------ | ---------------- | ---------------------------------------- |
| POST   | `/auth/register` | Register a new user                      |
| POST   | `/auth/login`    | Login & get token                        |
| POST   | `/auth/logout`   | Stateless logout (client discards token) |

### Tasks (Protected)

| Method | Endpoint      | Description                      |
| ------ | ------------- | -------------------------------- |
| POST   | `/tasks`      | Create task                      |
| GET    | `/tasks`      | Get all tasks for logged-in user |
| PUT    | `/tasks/{id}` | Update task status               |
| DELETE | `/tasks/{id}` | Delete task                      |

---

## ✅ Expected HTTP Status Codes

* **200 OK** – Success for GET/PUT/POST (updates)
* **201 Created** – On new task creation
* **400 Bad Request** – Invalid inputs or login credentials
* **401 Unauthorized** – No/invalid token when accessing `/tasks`
* **403 Forbidden** – Accessing another user's task
* **404 Not Found** – Task or resource not found

---

## 🧪 Tests

Run JUnit tests:

```bash
./mvnw test
```

---

## 🧭 Project Structure

```
src/main/java/com/example/taskapi/
 ├─ config/        # Security, JWT utils, filters
 ├─ controller/    # REST controllers
 ├─ service/       # Business logic
 ├─ repository/    # JPA repositories
 ├─ entity/        # Entities (User, Task)
 ├─ dto/           # Request/response DTOs
 └─ exception/     # Global exception handling
```

---

## 🔁 Postman Collection

A Postman collection with all endpoints is included in the repo:
📄 **`SpringBoot-Task-API.postman_collection.json`**

You can import it into Postman and test:

* Register/Login
* Copy JWT token into `jwtToken` variable
* Run task CRUD requests

---

## 🔗 Example `curl` Requests

### Register

```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"a@b.com","password":"pass","name":"A"}'
```

### Login

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"a@b.com","password":"pass"}'
```

### Create Task

```bash
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TOKEN>" \
  -d '{"title":"Buy milk","description":"From store","status":"OPEN"}'
```

---

## ✅ Submission Checklist

✔ All endpoints implemented & tested
✔ Passwords stored hashed (BCrypt)
✔ JWT token required for `/tasks`
✔ Global exception handling present
✔ README with setup & examples (this file)
✔ Public GitHub repo with clean commits

---

**Author / Contact:**
Sanjeev Kumar Ray — [GitHub Profile](https://github.com/sanjeevkumarray)

```

```
