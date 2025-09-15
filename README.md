# SpringBoot Task API

A small **Spring Boot** REST API for user authentication and task management — built for a take-home assignment.  
Features: JWT-based authentication, BCrypt password hashing, H2 in-memory DB, layered architecture (Controller → Service → Repository), and global exception handling.

---

## 🔧 Tech stack
- Java 17
- Spring Boot 3.x
- Spring Security (JWT)
- Spring Data JPA (H2)
- Maven
- JUnit (for tests, optional)

---

## 🚀 Quick start (GitHub clone + run)

```bash
# 1. Clone
git clone https://github.com/<your-username>/springboot-task-api.git
cd springboot-task-api

# 2. Build and run
./mvnw clean package
./mvnw spring-boot:run
# or use mvn if you don't have mvnw
mvn clean package
mvn spring-boot:run
```

App will run on `http://localhost:8080`. H2 console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`, user `sa` / no password)

---

## 🔐 Authentication flow (high level)
- Register user: `POST /auth/register` with `{ email, password, name }`. Passwords are hashed with BCrypt. Returns a JWT token.
- Login user: `POST /auth/login` with `{ email, password }`. Returns `{ "accessToken": "<JWT>" }` on success.
- Protect endpoints: Supply header `Authorization: Bearer <JWT>` to access `/tasks` endpoints.

---

## 📚 API Endpoints

### Auth
- `POST /auth/register` — Register a new user. Body example:
```json
{ "email": "user@example.com", "password": "mypassword", "name": "John Doe" }
```
- `POST /auth/login` — Login and get token. Body example:
```json
{ "email": "user@example.com", "password": "mypassword" }
```
- `POST /auth/logout` — Stateless (client can discard token). Optionally implement blacklist on server for forced invalidation.

### Tasks (Requires `Authorization: Bearer <token>`)
- `POST /tasks` — Create a new task. Body example:
```json
{ "title": "Buy milk", "description": "From store", "status": "OPEN" }
```
- `GET /tasks` — Get all tasks for the logged-in user.
- `PUT /tasks/{id}` — Update task (e.g., change status). Body example:
```json
{ "status": "DONE" }
```
- `DELETE /tasks/{id}` — Delete task (only owner can delete).

---

## ✅ Expected HTTP status behaviour (examples)
- 200 OK — Success responses for GET/PUT/POST (successful operations)
- 201 Created — (optional) when creating a resource
- 400 Bad Request — Invalid inputs, missing fields, invalid credentials at login
- 401 Unauthorized — No or invalid token when accessing protected endpoints
- 403 Forbidden — Accessing another user's task
- 404 Not Found — Task or resource not found

---

## 🧪 Running tests (if included)
```bash
./mvnw test
```

(If tests are not present yet, consider adding tests under `src/test/java` for at least the AuthController and TaskController.)

---

## ⚙️ Configuration (application.properties)
Key settings are in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.h2.console.enabled=true
jwt.secret=YOUR_SECRET_HERE
jwt.expiration=3600000
```

Make sure to change `jwt.secret` before production and do not commit secrets to GitHub (use environment variables or a secrets manager in real projects).

---

## 🧭 Project structure (recommended)
```
src/main/java/com/example/taskapi/
 ├─ config/        # Security, JWT utils, filters
 ├─ controller/    # REST controllers
 ├─ service/       # Business logic
 ├─ repository/    # JPA repositories
 ├─ entity/        # JPA entities (User, Task)
 ├─ dto/           # Request/response DTOs (optional)
 └─ exception/     # Global exception handling (@ControllerAdvice)
```

---

## 🔁 Postman / curl examples

### Register
```bash
curl -X POST http://localhost:8080/auth/register -H "Content-Type: application/json" -d '{ "email":"a@b.com","password":"pass","name":"A" }'
```

### Login
```bash
curl -X POST http://localhost:8080/auth/login -H "Content-Type: application/json" -d '{ "email":"a@b.com","password":"pass" }'
# Response: { "accessToken": "..." }
```

### Create Task (use token)
```bash
curl -X POST http://localhost:8080/tasks -H "Content-Type: application/json" -H "Authorization: Bearer <TOKEN>" -d '{ "title":"t","description":"d" }'
```

---

## 🧾 Example Git commit messages (clean history)
- feat: initial project scaffold, add pom and main class
- feat(auth): add registration and login with BCrypt and JWT
- feat(task): add task CRUD and repository
- chore: add README and example curl requests
- test: add AuthController unit tests (if added)

---

## ✅ Checklist for submission
- [ ] All endpoints implemented and tested manually with Postman/curl
- [ ] Passwords stored hashed (BCrypt)
- [ ] Token-based auth (JWT) implemented and required for `/tasks`
- [ ] Global exception handling present
- [ ] README with run instructions & example requests (this file)
- [ ] Public GitHub repository with clean commits

---

## Author / Contact
Sanjeev Kumar Ray — add your GitHub profile link here.

---
