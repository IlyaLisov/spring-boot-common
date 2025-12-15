# Spring Boot Common

[![Maven Build](https://github.com/ilyalisov/spring-boot-common/actions/workflows/build-and-test.yml/badge.svg)](https://github.com/ilyalisov/spring-boot-common/actions/workflows/build-and-test.yml)
[![Code Coverage](https://codecov.io/github/IlyaLisov/spring-boot-common/graph/badge.svg?token=0oAYzG58Tm)](https://codecov.io/github/IlyaLisov/spring-boot-common)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)

A comprehensive common library for Spring Boot projects that eliminates
boilerplate code and accelerates development of REST APIs with security
features.

## Table of Contents

* [Features](#features)
* [Quick Start](#quick-start)
* [Installation](#installation)
    * [Maven](#maven)
    * [Gradle](#gradle)
* [Core Components](#core-components)
    * [Entity Management](#entity-management)
    * [Criteria & Filtering](#criteria--filtering)
    * [JWT & Security](#jwt--security)
    * [Exception Hierarchy](#exception-hierarchy)
    * [Repository Specifications](#repository-specifications)
    * [Service Layer](#service-layer)
    * [DTOs and Mapping](#dtos-and-mapping)
* [Usage Examples](#usage-examples)
* [Configuration](#configuration)
* [License](#license)
* [Contributing](#contributing)

## Features

- **Pre-built Entities & Specifications** - Base entity model with common fields
  and JPA specifications
- **JWT Security** - Complete JWT token management with multiple token types,
  token service and many more
- **CRUD Services** - Generic CRUD operations with pagination and filtering
- **Exception Handling** - Comprehensive exception hierarchy with proper error
  responses
- **Validation Groups** - Flexible validation groups for different business
  scenarios
- **Mapper Interfaces** - Simplified DTO-entity mapping with field control

## Quick Start

Get your Spring Boot application up and running in minutes instead of hours.
This library provides all the essential components you need for a
production-ready REST API.

## Installation

### Maven

Add to your `pom.xml`:

```xml

<dependency>
    <groupId>io.github.ilyalisov</groupId>
    <artifactId>spring-boot-common</artifactId>
    <version>0.1.2</version>
</dependency>
```

### Gradle

Add to your `build.gradle` or `build.gradle.kts`:

```groovy
implementation("io.github.ilyalisov:spring-boot-common:0.1.2")
```

## Core Components

### Entity Management

#### BaseEntity

The foundation for all your JPA entities with built-in common fields:

```java

@MappedSuperclass
public abstract class BaseEntity {
    private UUID id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Status status;
    // ... getters, setters, and lifecycle callbacks
}
```

By extending of `BaseEntity` you can create your own entities.

#### Status

Comprehensive status management for your entities:

- `ACTIVE` - Entity is active and visible
- `NOT_ACTIVE` - Pending activation (e.g., email verification)
- `BLOCKED` - Temporarily blocked
- `DELETED` - Soft deleted
- `UNDER_MODERATION` - Awaiting moderation approval
- `MODERATION_DECLINED` - Rejected during moderation
- `DRAFT` - Work in progress

### Criteria & Filtering

#### Criteria

There is `Criteria` class. You can build handy criteria for your domain by
extending this class. Base class have `pageId`, `perPage`, `query`, `status`
and `authorId` params.

Extendable criteria builder for complex queries with built-in pagination:

```java
public class UserCriteria extends Criteria {
    private String email;
    private String username;
    private LocalDate registeredFrom;
    private LocalDate registeredTo;
    // ... your custom fields
}
```

You need to implement `toSpec` method that provides `Specification`
corresponding to criteria. It allows you to use one method instead of
manually creating a specification.

### JWT & Security

Complete JWT token management with multiple token types:

- `ACCESS` - Short-lived access tokens
- `REFRESH` - Long-lived refresh tokens
- `ACTIVATION` - Account activation tokens
- `PASSWORD_RESET` - Password recovery tokens
- `CUSTOM` - Custom application tokens

You have a `TokenService` bean at a startup. Just provide next properties in
`application.yaml`:

```yaml
security:
  jwt:
    secret: some-encoded-in-base-64-string
    access: 30m
    refresh: 1d
    activation: 1d
    reset: 1h
```

You have a default `BCryptEncoder` `PassowrdEncoder` bean at a startup. You can
create your own `PasswordEncoder` bean and use it instead.

We use [this jwt library](https://github.com/ilyalisov/jwt) for dealing with
tokens.

### Exception Hierarchy

Comprehensive exception system for proper error handling:

```java
// Resource not found scenarios
throw new ResourceNotFoundException("User not found with id: " + userId);

// Authentication and authorization
throw new AccessDeniedException("Insufficient permissions");

// Business rule violations
throw new ResourceAlreadyExistsException("User already exists with this email");
throw new DataNotValidException("Invalid input data");
throw new TokenNotValidException("Expired or invalid token");
throw new NotEnoughMoneyException("Insufficient balance for transaction");

// System errors
throw new UploadException("Failed to upload file to storage");
```

You can use `MessageDto` class for error responding in `ControllerAdvice`.

### Repository Specifications

We prefer to use JPA Specification to filter data. So there are some useful
common specifications:

- `hasStatus(Status)`, `isActive()`, `isNotActive()`, `isBlocked()`,
  `isDeleted()`, `isUnderModeration()`, `isModerationDeclined()`, `isDraft()` -
  specifications for filtering by `status` field
- `hasAuthorId(UUID)` - specification for filtering by entity's `author.id`
  field
- `inPeriod(Comparable, Comparable)` - specification for filtering by `created`
  field
- `activeInPeriod(Comparable, Comparable)` - specification combining active and
  created in period entities
- `containsQuery(String, String)` - specification for filtering entities by
`fts` column - full text search from Postgresql

There is utility classes for joins in specifications.

### Service Layer

#### CrudService<Entity, Criteria>

Generic CRUD operations with built-in best practices:

```java
public interface CrudService<E extends BaseEntity, C extends Criteria> {

    // Safe entity retrieval
    E getById(UUID id); // Throws ResourceNotFoundException if not found

    E getById(UUID id, boolean safe); // Conditional exception throwing

    // Paginated queries
    Page<E> getAll(C criteria);

    // Counting and aggregation
    long countAll(C criteria);

    // Lifecycle management
    E create(E entity);

    E update(E entity);

    void delete(UUID id);
}
```

#### Specialized Service Interfaces

`Blockable` Service - For entities requiring blocking functionality:

```java
void block(UUID id);    // Mark as BLOCKED

void unblock(UUID id);  // Restore to ACTIVE
```

`Moderatable` Service - For content moderation workflows:

```java
void allow(UUID id);    // Approve → ACTIVE

void disallow(UUID id); // Reject → MODERATION_DECLINED
```

### DTOs and Mapping

#### Mappable

Flexible mapping interface with batch operations:

```java
public interface Mappable<E, D> {
    E toEntity(D dto);

    List<E> toEntity(List<D> dtos);

    D toDto(E entity);

    List<D> toDto(List<E> entities);
}
```

BaseMappable<Entity extends BaseEntity, DTO>

Extended mapper that automatically ignores audit fields (status, created,
updated) when mapping from DTO to entity.

#### Validation Groups

Structured validation for different business scenarios:

- `OnCreate` - Validation rules for new entity creation
- `OnUpdate` - Validation rules for existing entity updates
- `OnPublish` - Validation rules before publishing content
- `NestedObjectId` - Validation for entity references in nested objects
- `OnAnyId` - Flexible ID validation (optional or required)

## Usage Examples

#### Complete Entity Setup

```java
@Entity
public class User extends BaseEntity {
    private String username;
    private String email;
    private String password;
// ... your fields
}

public class UserCriteria extends Criteria {
    private String email;
    private Boolean active;
// ... your criteria
}

@Service
public class UserService implements CrudService<User, UserCriteria> {
// Inherits all CRUD operations + add your business logic
}
```

#### Custom Specification Usage

```java
@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
}

@Service
public class UserService {

    public Page<User> findActiveUsersRegisteredLastMonth(UserCriteria criteria) {
        Specification<User> spec = isActive()
                .and(inPeriod(LocalDateTime.now().minusMonths(1), LocalDateTime.now()))
                .and(hasEmailLike(criteria.getEmail()));

        return userRepository.findAll(spec, criteria.getPageable());
    }
}
```

#### Exception Handling in Controllers

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MessageDto> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MessageDto(ex.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<MessageDto> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new MessageDto(ex.getMessage()));
    }
}
```

## License

This project is licensed under the MIT License - see the [LICENSE](./LICENSE)
file for details.

## Contribution

We welcome contributions! Please feel free to submit issues and enhancement
requests.

To contribute, make a fork and open a pull request. You can find
issues [here](https://github.com/ilyalisov/spring-boot-common/issues).

Make sure, you follow project's codestyle.