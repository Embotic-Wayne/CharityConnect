# CharityConnect 🕊️

A secure crowdfunding backend inspired by GoFundMe — allowing users to create fundraising campaigns, donate, and manage campaigns with admin moderation.

---

## 🖥️ Screenshot  
*(You can add one later — e.g., Swagger UI, Postman demo, or Docker dashboard)*  

---

## ⚙️ Tech Stack
- **Java 21** – Core language  
- **Spring Boot** – Backend framework  
- **Spring Security (JWT)** – Authentication & Role-based Access Control  
- **PostgreSQL** – Relational database  
- **JPA / Hibernate** – ORM for persistence  
- **Docker** – Containerized PostgreSQL  
- **Maven** – Build automation & dependency management  
- **JUnit 5 / Mockito / JaCoCo** – Testing suite (88% line coverage)  
- **Swagger UI** – API documentation  
- **GitHub Actions (optional)** – CI/CD integration  

---

## 🚀 Getting Started

### 1️⃣ Clone the Repository

git clone https://github.com/Embotic-Wayne/CharityConnect.git
cd CharityConnect

2️⃣ Start the Database

Make sure Docker Desktop is running, then:

docker compose up -d

3️⃣ Configure the Application

Create a file at src/main/resources/application.yml (or copy from application-example.yml):

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/charitydb
    username: charity
    password: charitypass
  jpa:
    hibernate:
      ddl-auto: update
app:
  jwt:
    secret: replace-with-a-long-secret-key
    expirationMillis: 3600000

4️⃣ Run the Server
./mvnw spring-boot:run

5️⃣ Open the API Docs

Navigate to:
👉 http://localhost:8080/swagger-ui

🧪 Testing

Run all tests:

./mvnw test


Generate a coverage report:

./mvnw clean verify


Then open target/site/jacoco/index.html in your browser to view results.
