# Web Quiz Engine

A web-based quiz application that allows users to create, manage, and solve quizzes. 

Thanks to **[the Hyperskill Team](https://hyperskill.org/)** for their support and guidance on this project. 

## Features

- Quiz creation and management
- Multiple-choice questions support
- User authentication and authorization
- Quiz solving and tracking
- RESTful API architecture

## Technical Stack

- Language: Kotlin
- Framework: Spring Boot
- Database: JPA/Hibernate for persistence
- Authentication: Spring Security

## Detailed Installation Guide

### Prerequisites

- JDK 11 or higher
- Gradle/Maven (build tool)
- Your preferred IDE (IntelliJ IDEA recommended for Kotlin)
- Database server (e.g., PostgreSQL, MySQL)

### Step-by-Step Installation

1. Clone the repository:
   ```bash
   git clone [repository-url]
   cd webquizengine

2. Configure Database:
  - Create a new database for the application
  - Update src/main/resources/application.properties:
```spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```
3. Build the project:
```
./gradlew bootRun
```
The application will be available at http://localhost:8080

---

## API Documentation

### Authentication

All endpoints except registration require authentication.

### Register a New User

```http request
POST /api/register
Content-Type: application/json

{
    "email": "user@example.com",
    "password": "password123"
}
```
### Quiz Operations

#### Create a New Quiz

```http request
POST /api/quizzes
Content-Type: application/json

{
    "title": "Quiz Title",
    "text": "Question text",
    "options": [
        "Option 1",
        "Option 2",
        "Option 3",
        "Option 4"
    ],
    "answer": [1, 2]
}
```

#### Get a Quiz by ID

```http request
GET /api/quizzes/{id}
```

#### Get All Quizzes (Paginated)

```http request
GET /api/quizzes/{id}
```

#### Solve a Quiz

```http request
POST /api/quizzes/{id}/solve
Content-Type: application/json

{
    "answer": [1, 2]
}
```
#### Delete a Quiz

```http request
DELETE /api/quizzes/{id}
```

#### Get Completion History

````http request
GET /api/quizzes/completed?page={pageNumber}
````

### Response Formats

#### Quiz Object

```json
{
    "id": 1,
    "title": "Quiz Title",
    "text": "Question text",
    "options": ["Option 1", "Option 2", "Option 3", "Option 4"]
}
```

#### Solve Response

```json
{
    "success": true,
    "feedback": "Congratulations, you're right!"
}
```

#### Error Response

```json
{
    "timestamp": "2023-12-20T10:00:00.000+00:00",
    "status": 404,
    "error": "Not Found",
    "message": "Quiz not found",
    "path": "/api/quizzes/999"
}
```

## Configuration Options

### Application Properties

```properties
# Server configuration
server.port=8080

# Pagination
spring.data.web.pageable.default-page-size=10
spring.data.web.pageable.max-page-size=50
```

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch 
5. Create a new Pull Request

## Support

For support, please open an issue in the repository or contact the development team.