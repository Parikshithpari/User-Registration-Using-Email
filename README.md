# User-Registration-Using-Email
Secure and scalable user registration system using Spring Boot with email verification support.

<br>

This project is a Spring Boot-based backend service that enables secure user registration with email verification. It's designed using a modular architecture to support scalability and can be easily integrated into larger systems.

<br>

## ✨ Features

- User account creation with email-based verification
- Token-based verification system with expiry
- Secure password hashing using BCrypt
- MySQL database integration with JPA
- RESTful APIs for registration and confirmation
- Custom error handling and validation

## ⚙️ Tech Stack

- **Spring Boot**
- **Spring Security**
- **Spring Data JPA**
- **H2 Database**
- **JavaMailSender**
- **Lombok**

## 🔒 How It Works

1. A user registers with an email and password.
2. The system sends a verification email with a token.
3. The user confirms their email using the provided link.
4. The account becomes active upon successful verification.

## 🚀 Getting Started

### Prerequisites

- Java 21
- Maven
- H2 Database

## ✅ Testing Summary

All controller and service layer endpoints are covered by:

- **Unit Tests** using `@WebMvcTest`, `@MockBean`
- **Integration Tests** using `@SpringBootTest`, `MockMvc`
- **Edge Case Validation**: Invalid tokens, duplicate emails, etc.
- All tests passed successfully ✔️

Run tests with:
```bash
mvn test


