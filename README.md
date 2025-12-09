# 🥗 EatWise Backend API

EatWise is a Spring Boot application developed for the SWE course group project. It serves as a backend API for managing personalized eating plans, user profiles, and diet history.

## 🛠️ Tech Stack

*   **Java:** 21
*   **Framework:** Spring Boot 3.5.7
*   **Database:** PostgreSQL
*   **Build Tool:** Gradle
*   **Security:** Spring Security & JWT (JSON Web Tokens)

---

## 📋 Prerequisites

Before running the application, ensure you have the following installed:

1.  **Java JDK 21**: [Download Here](https://www.oracle.com/java/technologies/downloads/#java21)
2.  **PostgreSQL**: [Download Here](https://www.postgresql.org/download/)
3.  **Git**: [Download Here](https://git-scm.com/downloads)

---

## 🚀 Getting Started

Follow these steps to set up and run the project on your local machine.

### 1. Clone the Repository

Open your terminal and run:

```bash
git clone https://github.com/jabiyev11/EatWise.git
cd EatWise
```

### 2. Database Setup

**Crucial Step:** You must create a specific database for this application.

1.  Open your PostgreSQL tool (PgAdmin or Command Line).
2.  Run the following SQL command to create the required database:

```sql
CREATE DATABASE eatwise;
```

3.  Ensure your PostgreSQL service is running on port `5432`.

### 3. Environment Configuration (Important!)

This application uses **Environment Variables** for sensitive data (Database credentials and Security keys). You do not need to edit the code; you simply need to provide these values when running the app.

You need to fill in these 3 variables:

| Variable | Description |
| :--- | :--- |
| `DB_USERNAME` | Your PostgreSQL username (usually `postgres`) |
| `DB_PASSWORD` | Your PostgreSQL password |
| `JWT_SECRET` | A secure, random string used to sign login tokens |

#### 🔐 How to generate a JWT Secret Key
You need a long, secure string (at least 32 characters) for the JWT secret. Run one of the commands below in your terminal to generate one:

**Mac / Linux / Git Bash:**
```bash
openssl rand -base64 32
```

**Windows PowerShell:**
```powershell
[Convert]::ToBase64String((1..32 | %{ [byte](Get-Random -Max 256) }))
```

*Copy the output string. You will use it in the next step.*

---

## 🏃 How to Run the Application

You can run the application directly from the command line without an IDE. Choose the method for your operating system.

### Option A: macOS / Linux / Git Bash

Replace `your_password` and `your_generated_secret_key` with your actual values:

```bash
export DB_USERNAME=postgres
export DB_PASSWORD=your_password
export JWT_SECRET=your_generated_secret_key

./gradlew bootRun
```

### Option B: Windows (Command Prompt)

```cmd
set DB_USERNAME=postgres
set DB_PASSWORD=your_password
set JWT_SECRET=your_generated_secret_key

gradlew bootRun
```

### Option C: Windows (PowerShell)

```powershell
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="your_password"
$env:JWT_SECRET="your_generated_secret_key"

.\gradlew bootRun
```

---

## 🛑 Troubleshooting

### Error: `zsh: permission denied: ./gradlew`
If you are on Mac or Linux and receive a permission denied error when trying to run the app, it means the Gradle wrapper script is not executable.

**Solution:** Run the following command in your project folder:

```bash
chmod +x gradlew
```

Then try running `./gradlew bootRun` again.

---

## ✅ Verifying the Application

Once the application starts, you should see a log message similar to:
`Tomcat started on port 8080`

The API is now accessible at: `http://localhost:8080`

### Quick API Check

You can test the API using **Postman** or **cURL**.

**1. Register a User:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
-H "Content-Type: application/json" \
-d '{
    "email": "user@example.com",
    "password": "password123",
    "firstName": "Jane",
    "lastName": "Doe"
}'
```

**2. Login:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
-H "Content-Type: application/json" \
-d '{
    "email": "user@example.com",
    "password": "password123"
}'
```

---

## 📂 Project Structure

*   `src/main/java/com/eatWise`
    *   `client/`: Feign Client for external AI/RAG service.
    *   `config/`: Security configuration.
    *   `controller/`: API endpoints.
    *   `domain/`: Database entities.
    *   `dto/`: Data Transfer Objects for API requests/responses.
    *   `repository/`: Database interface layers.
    *   `service/`: Business logic.