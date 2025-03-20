# AI Enhanced Mobile Backend

A Spring Boot-based backend with AI capabilities for mobile applications, built using Clean Architecture, Hexagonal Architecture, and Domain-Driven Design principles.

## Architecture

This project follows:
- **Clean Architecture**: Separation of concerns with layered architecture
- **Hexagonal Architecture**: Ports and adapters pattern for flexible infrastructure
- **Domain-Driven Design**: Focus on the core domain model with aggregates and value objects

### Project Structure

```
src/main/java/com/example/aimobilebackend/
├── application/            # Application layer
│   ├── dto/                # Data Transfer Objects
│   ├── exception/          # Application-specific exceptions
│   ├── port/               # Ports (in/out)
│   └── service/            # Application services (use cases)
├── domain/                 # Domain layer
│   ├── exception/          # Domain-specific exceptions
│   ├── event/              # Domain events
│   ├── model/              # Domain model (aggregates, entities, value objects)
│   ├── repository/         # Repository interfaces
│   └── service/            # Domain services
├── infrastructure/         # Infrastructure layer
│   ├── ai/                 # AI service implementations
│   ├── config/             # Configuration classes
│   ├── persistence/        # Database implementations
│   └── security/           # Security configurations
└── interfaces/             # Interfaces layer
    ├── mapper/             # Object mappers
    └── rest/               # REST controllers and DTOs
```

## Features

- RESTful API for user management
- AI-powered features:
  - Text generation
  - Text summarization
  - Recommendations
- Comprehensive error handling
- Test coverage with unit, integration, and functional tests

## Dependencies

- Spring Boot 3.2.3
- Spring Data JPA
- Spring Web
- Spring Validation
- Spring AI 0.8.1
- H2 Database
- Lombok
- JUnit Jupiter and Mockito for testing
- RestAssured for API testing
- Testcontainers for integration tests

## Getting Started

### Prerequisites

- JDK 17 or higher
- Gradle
- OpenAI API key

### Configuration

Set your OpenAI API key in the application.yml file or as an environment variable:

```
export OPENAI_API_KEY=your-api-key-here
```

### Building and Running

```bash
# Build the project
./gradlew build

# Run the application
./gradlew bootRun
```

### Testing

```bash
# Run tests
./gradlew test

# Generate test coverage report
./gradlew jacocoTestReport
```

## API Endpoints

### User Management

- `POST /api/users` - Create a new user
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users` - Get all users
- `PUT /api/users/{id}/username?username=newUsername` - Update username
- `PUT /api/users/{id}/email?email=new@example.com` - Update email
- `DELETE /api/users/{id}` - Delete user

### AI Features

- `POST /api/ai/generate` - Generate text based on prompt
- `POST /api/ai/summarize` - Summarize provided text
- `POST /api/ai/recommend` - Get recommendations based on context

## Design Decisions

- **Immutable Value Objects**: Ensures integrity and thread safety
- **Rich Domain Model**: Business logic encapsulated in the domain layer
- **Port and Adapter Pattern**: Clear separation between application core and external systems
- **CQRS-inspired Structure**: Separate interfaces for commands and queries
- **Test-Driven Development**: Comprehensive test coverage