# ADR 0001: Hexagonal Architecture (Ports and Adapters)

## Status

Accepted

## Context

We need an architecture that:
- Isolates business logic from external concerns
- Is maintainable and testable
- Allows swapping of external dependencies without affecting domain logic
- Provides clear boundaries between application layers

## Decision

We will implement a Hexagonal Architecture (Ports and Adapters) with the following layers:

```
┌─────────────────────────────────────────────────┐
│                                                 │
│  ┌─────────────────────────────────────────┐    │
│  │                                         │    │
│  │  ┌─────────────────────────────────┐    │    │
│  │  │                                 │    │    │
│  │  │    Domain Layer (Core)          │    │    │
│  │  │                                 │    │    │
│  │  └───────────────┬─────────────────┘    │    │
│  │                  │                      │    │
│  │  Application Layer                      │    │
│  │                  │                      │    │
│  └──────────────────┼──────────────────────┘    │
│                     │                           │
│  Adapters           │                           │
│  ┌──────────────────┼──────────────────────┐    │
│  │ Infrastructure   │                      │    │
│  └──────────────────┘  ┌──────────────┐    │    │
│                        │ Interfaces   │    │    │
│                        └──────────────┘    │    │
│                                            │    │
└────────────────────────────────────────────┘    │
                                                  │
┌────────────────────────────────────────────────┐│
│ External Systems (DB, AI Services, etc.)       ││
└────────────────────────────────────────────────┘┘
```

### Layer Definitions

#### 1. Domain Layer (Core)
- Contains pure business logic independent of external systems
- Components:
  - **Domain Models/Aggregates (`User.java`)**: Business entities with behavior
  - **Value Objects (`UserId.java`)**: Immutable objects representing domain values
  - **Domain Services**: Complex operations across multiple entities
  - **Domain Events**: Business significant events
  - **Repository Interfaces**: Defines persistence operations needed by domain
- Rules:
  - No dependencies on external frameworks or systems
  - No references to other layers
  - Defines interfaces that will be implemented by outer layers

#### 2. Application Layer
- Orchestrates domain objects to perform use cases
- Components:
  - **"In" Ports (`UserUseCase.java`, `AiAssistantUseCase.java`)**: Use case interfaces consumed by UI/controllers
  - **Application Services (`UserApplicationService.java`)**: Implements use cases, orchestrates domain operations
- Rules:
  - Depends only on the domain layer
  - Contains no business logic, only orchestration
  - Translates between domain and external representations using DTOs
  - Uses domain repository interfaces directly (no redundant "out" ports)

#### 3. Infrastructure Layer (Adapters)
- Implements interfaces defined by domain/application layers
- Components:
  - **Persistence Adapters (`UserPersistenceAdapter.java`)**: Implements repository interfaces
  - **External Service Adapters (`OpenAiAssistantAdapter.java`)**: Interfaces with external APIs
  - **Configuration (`AiConfig.java`, `DomainConfig.java`)**: Wires components together
- Rules:
  - Contains implementations of domain-defined interfaces
  - Maps between domain models and external representations
  - Contains framework-specific code

#### 4. Interfaces Layer (Adapters)
- User-facing components that drive the application
- Components:
  - **REST Controllers (`UserController.java`, `AiAssistantController.java`)**: Handle HTTP requests
  - **DTOs (`UserRequest.java`, `UserResponse.java`)**: Data transfer objects
  - **Exception Handlers**: Handle application exceptions
- Rules:
  - Depends on application layer interfaces (use cases)
  - Converts between API formats and application representations
  - Contains no business logic

## Flow of Control

```
   ┌────────────┐      ┌────────────┐      ┌────────────┐      ┌────────────┐
   │ Interface  │      │Application │      │  Domain    │      │Infrastructure│
   │  Layer     │─────▶│   Layer    │─────▶│   Layer    │─────▶│   Layer   │
   │(Controller)│      │ (Use Case) │      │  (Model)   │      │  (Adapter) │
   └────────────┘      └────────────┘      └────────────┘      └────────────┘
         │                                        ▲                   │
         │                                        │                   │
         │                                        │                   │
         └────────────────────────────────────────┴───────────────────┘
                      Dependency Direction (inward)
```

## Concrete Example

1. **User Request Flow**:
   ```
   UserController → UserUseCase (interface) → UserApplicationService (impl) → User (domain) → UserRepository (domain interface) → UserPersistenceAdapter (impl) → Database
   ```

2. **AI Assistant Request Flow**:
   ```
   AiAssistantController → AiAssistantUseCase (interface) → OpenAiAssistantAdapter (impl) → OpenAI API
   ```

## Benefits

1. **Separation of Concerns**: Each layer has clear responsibilities
2. **Testability**: Domain logic can be tested in isolation with mocks
3. **Flexibility**: External implementations can be swapped without affecting business logic
4. **Independence from Frameworks**: Core business logic doesn't depend on frameworks
5. **Dependency Inversion**: Dependencies point inward toward domain

## Consequences

1. **Learning Curve**: Architecture may be unfamiliar to new team members
2. **Thoughtful Interface Design**: Care must be taken to avoid redundant interfaces
3. **Refactoring Considerations**: 
   - We eliminated redundant "out" ports in the application layer
   - Domain repository interfaces are used directly by application services
   - This simplifies the design while maintaining clean architecture principles
4. **Balance of Abstraction**: We aim for the minimal set of interfaces needed to achieve separation of concerns

## References

- Alistair Cockburn's Hexagonal Architecture: https://alistair.cockburn.us/hexagonal-architecture/
- Ports and Adapters Pattern: https://web.archive.org/web/20180822100852/http://alistair.cockburn.us/Hexagonal+architecture
- Domain-Driven Design (Eric Evans)