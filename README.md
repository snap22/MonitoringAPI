# Endpoints monitoring service

## Setup
#### Docker compose
Run command `docker-compose up`

#### Local
Create a MySQL database with name `monitoring` and run the [SQL script](db/setup.sql)
(or create database `monitoring` and specify your own credentials in [application.properties](src/main/resources/application.properties))

Run the application via maven wrapper
```shell
./mvnw spring-boot:run
```

Or run the jar file directly 
```shell
java -jar package/applifting-1.0.0.jar
```

The application runs at `http://localhost:8080`


## API Endpoints
All endpoints are defined in [postman collection](./Endpoints.postman_collection.json)

##### GET /users/me
Get current user information

##### GET /users/me/endpoints
Get current user endpoints

##### GET /users/me/endpoints/:id
Get current user endpoint by id

##### POST /users/me/endpoints
Create new endpoint to monitor

##### PUT /users/me/endpoints/:id
Edit current user endpoint by id

##### DELETE /users/me/endpoints/:id
Delete current user endpoint by id

##### GET /users/me/endpoints/:id/results
Get current user endpoint newest 10 results


## Project structure
- config - configuration classes
- controllers - REST controllers
- dto - data transfer objects for requests and responses
- entities - JPA entities
- exceptions - custom exceptions and global exception handling
- mappers - MapStruct mappers
- repositories - JPA repositories
- schedulers - scheduled tasks
- security - security logic & filters
  - [BearerTokenFilter](src/main/java/org/example/security/filters/BearerTokenFilter.java) - service for checking monitored endpoints
- services - business logic
  - [CheckerService](src/main/java/org/example/services/implementation/CheckerService.java) - service for checking monitored endpoints
  - [EndpointService](src/main/java/org/example/services/implementation/EndpointService.java) - service for managing endpoints
  - [MonitoringResultService](src/main/java/org/example/services/implementation/MonitoringResultService.java) - service for managing results
  - [UserService](src/main/java/org/example/services/implementation/UserService.java) - service for managing users

### Used technologies
- Spring Boot
- Spring Security - security
- Spring Data JPA - repositories & entities
- Spring Webflux - HTTP client
- MapStruct - mapping
- Lombok - boilerplate code reduction
- Flyway - database migrations