# CharityConnect 🕊️

A secure crowdfunding backend inspired by GoFundMe — allowing users to create fundraising campaigns, donate, and manage campaigns with admin moderation.
---

## ⚙️ Tech Stack
- **Java 21** – Core language  
- **Spring Boot** – Backend framework  
- **Spring Security (JWT)** – Authentication & Role-based Access Control  
- **PostgreSQL** – Relational database  
- **JPA / Hibernate** – ORM for persistence  
- **Docker** – Containerized PostgreSQL  
- **Maven** – Build automation & dependency management  
- **JUnit 5 / Mockito / JaCoCo** – Testing suite 
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

3️⃣  Run the Server
./mvnw spring-boot:run

4️⃣ Open the API Docs

Navigate to:
👉 http://localhost:8080/swagger-ui

🧪 Testing

Run all tests:

./mvnw test


Generate a coverage report:

./mvnw clean verify


Then open target/site/jacoco/index.html in your browser to view results.
