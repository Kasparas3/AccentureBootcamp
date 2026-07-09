# Shelter Testing Starter — Advanced Unit Testing

This Spring Boot project is pre-built for Advanced Unit Testing.

## Domain

A simple animal shelter with:
- Animals that can be created, listed, and adopted.
- A notification client that sends adoption alerts to an external system.

## Architecture for Testing

| Layer | Class | Test Style |
|-------|-------|------------|
| Service | `AnimalService` | Mockito unit tests |
| Controller | `AnimalController` | MockMvc with `@WebMvcTest` |
| Repository | `AnimalRepository` | `@DataJpaTest` |
| Full flow | create → adopt → notify | `@SpringBootTest` with `@MockitoBean` |

## External Dependency Seam

`NotificationClient` is the outbound interface.  
In production, `LoggingNotificationClient` logs to stdout.  
In integration tests, can be replaced with `@MockitoBean`.

## Running Tests

```bash
# Run all tests
./mvnw test

# Run only service tests
./mvnw test -Dtest=AnimalServiceTest

# Run only controller tests
./mvnw test -Dtest=AnimalControllerTest

# Run only repository tests
./mvnw test -Dtest=AnimalRepositoryTest

# Run only integration tests
./mvnw test -Dtest=AdoptionIntegrationTest
```

## Project Structure

```
src/main/java/lv/bootcamp/shelter/
├── client/
│   ├── NotificationClient.java          (interface — external seam)
│   └── LoggingNotificationClient.java   (default impl)
├── controller/
│   ├── AnimalController.java            (REST endpoints)
│   └── GlobalExceptionHandler.java      (error responses)
├── dto/
│   ├── AdoptionRequest.java
│   ├── AnimalCreateRequest.java
│   └── AnimalResponse.java
├── model/
│   ├── Animal.java                      (JPA entity)
│   ├── AnimalStatus.java
│   └── AnimalType.java
├── repository/
│   └── AnimalRepository.java            (Spring Data JPA)
├── service/
│   ├── AnimalService.java               (business logic)
│   └── AnimalNotFoundException.java
└── ShelterTestingApplication.java

src/test/java/lv/bootcamp/shelter/
├── controller/
│   └── AnimalControllerTest.java        (@WebMvcTest)
├── repository/
│   └── AnimalRepositoryTest.java        (@DataJpaTest)
├── service/
│   └── AnimalServiceTest.java           (Mockito unit test)
└── AdoptionIntegrationTest.java         (@SpringBootTest)
```

## Prerequisites

- Java 21
- Maven 3.9+
