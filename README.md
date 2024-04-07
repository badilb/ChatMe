# ChatMe Application

This repository contains configuration files and code for deploying the ChatMe application using Docker Compose.

## Docker Compose Configuration

The `docker-compose.yml` file defines the services required for the ChatMe application:

- **psql**: PostgreSQL database service.
- **redis**: Redis cache storage service.
- **elasticsearch**: Elasticsearch service.
- **kibana**: Kibana service for Elasticsearch.
- **logstash**: Logstash service for processing logs.
- **prometheus**: Prometheus service for monitoring.
- **grafana**: Grafana service for visualization.

## Configuration Files

- `prometheus.yaml`: Prometheus configuration file specifying scraping targets.
- `logstash.yml`: Logstash configuration file for HTTP host and monitoring.
- `logstash.conf`: Logstash pipeline configuration file for input and output.
- `application.yaml`: Spring Boot application configuration file for database settings and management endpoints.

## Backend

The `backend` directory contains Java source code for the ChatMe application, including aspects, components, configurations, controllers, and exception handlers.

### Aspects

- `RequestsLoggingAspect.java`: Aspect for logging HTTP requests and responses, including timing and metrics.

### Configs
#### Security Configs
- `AuthenticationManagerConfig.java`: Configuration for custom authentication manager.
- `CorsConfig.java`: Configuration for handling CORS.
- `JdbcSessionConfig.java`: Configuration for JDBC session management.
- `PasswordEncoderConfig.java`: Configuration for password encoding.
- `SecurityFilterChainConfig.java`: Configuration for security filter chains.
#### Redis Configs
- `RedisConfig.java`: Configuration for enabling caching with Redis.

### Controllers

- `AuthController.java`: Controller for handling authentication endpoints such as login, register, and logout.
- `ChatController.java`: Controller for managing chat-related endpoints such as fetching, creating, updating, and deleting chats.
- `GlobalExceptionHandlerController.java`: Controller advice for handling exceptions globally.
- `MessageController.java`: Controller for handling message-related endpoints such as fetching, sending, updating, and deleting messages.

### Components

- `MessageLogic.java`: Component for message validation logic.
- `MessageLogic.java`: Component for validating message ownership.

### Exception Handling

- `ChatNotFoundException.java`: Exception thrown when a chat is not found by UUID.
- `MessageNotFoundException.java`: Exception thrown when a message is not found by ID.
- `RoleNotFoundException.java`: Exception thrown when a role is not found.
- `UserNotFoundException.java`: Exception thrown when a user is not found by username.
- `UsernameTakenException.java`: Exception thrown when a username is already taken during registration.

### Models

#### Entity Classes

- `Chat.java`: Entity class representing a chat in the application.
- `Message.java`: Entity class representing a message in the application.
- `UserEntity.java`: Entity class representing a user in the application.

#### DTO Classes

- `ChatDTO.java`: Data Transfer Object (DTO) class representing chat data to be transferred over the network.
- `LoginDTO.java`: DTO class representing login data sent by the client.
- `MessageDTO.java`: DTO class representing message data to be transferred over the network.
- `RegistrationDTO.java`: DTO class representing user registration data sent by the client.
- `UserEntityDTO.java`: DTO class representing user data to be transferred over the network.

#### Enum Classes
- `RoleType.java`: Enum representing the types of roles in the application, such as ADMIN and USER.

### Repositories

#### ChatRepository.java

- Responsible for handling database operations related to the `Chat` entity.
- Provides methods to find a chat by its UUID and to retrieve a DTO representation of a chat by its UUID.

#### MessageRepository.java

- Responsible for handling database operations related to the `Message` entity.
- Provides a method to retrieve a DTO representation of a message by its ID.

#### RoleRepository.java

- Responsible for handling database operations related to the `Role` entity.
- Provides a method to find a role by its name.

#### UserRepository.java

- Responsible for handling database operations related to the `UserEntity` entity.
- Provides methods to find a user by their username and to retrieve a DTO representation of a user by their username.

### Services

#### ChatService.java

- Provides methods for creating, finding, and deleting chats.
- Also provides methods to find a chat and its DTO representation by UUID.

#### MessageService.java

- Provides methods for managing messages within a chat, such as finding, saving, updating, and deleting messages.
- Also provides a method to retrieve a DTO representation of a message by its ID.

#### RoleService.java

- Provides a method for finding a role by its type.

#### UserService.java

- Provides methods for finding, saving, and deleting users.

### Utils

#### SessionUtil.java

- Provides a utility method to retrieve the username of the currently authenticated user from the session.


## Frontend

The `frontend` directory contains a package.json file defining dependencies and scripts for building and running the frontend application.

## Usage

To deploy the ChatMe application, follow these steps:

1. Ensure Docker and Docker Compose are installed on your system.
2. Clone this repository to your local machine.
3. Navigate to the repository directory.
4. Run `docker-compose up -d` to start the services in detached mode.
5. Access the application frontend at `http://localhost:8080`.
6. Access Grafana for monitoring at `http://localhost:3000` (username: admin, password: admin).
7. Access Kibana for Elasticsearch logs at `http://localhost:5601`.
8. Access Prometheus for monitoring at `http://localhost:9090`.

## Notes

- Make sure no other services are running on ports 5433, 6379, 9200, 5601, 5044, 9090, and 3000 to avoid conflicts.
- Additional configurations and environment variables can be adjusted as needed in the respective files.
