# Spring Boot Common

[![mvn](https://github.com/ilyalisov/spring-boot-common/actions/workflows/build-and-test.yml/badge.svg)](https://github.com/ilyalisov/spring-boot-common/actions/workflows/build-and-test.yml)
[![codecov](https://codecov.io/github/IlyaLisov/spring-boot-common/graph/badge.svg?token=0oAYzG58Tm)](https://codecov.io/github/IlyaLisov/spring-boot-common)

This library provides common classes for Spring Boot project, allowing you to
save your time during implementing basic features.

Look under content to see what you can do.

* [Description](#description)
* [Install](#install)
* [Components](#components)
* [License](#license)
* [Contribution](#contribution)

## Description

Aim of this library is to decrease amount of boilerplate code used to start a
new project. As we see, it needs about 20-30 classes to implement to start a
simple REST with Spring Security. Now you need to only import this library and
configure some beans.

## Install

Add dependency to `pom.xml` if you use Maven:

```xml

<dependency>
    <groupId>io.github.ilyalisov</groupId>
    <artifactId>spring-boot-common</artifactId>
    <version>0.1.0</version>
</dependency>
```

Or this script if you use Gradle:

```groovy
 implementation("io.github.ilyalisov:spring-boot-common:0.1.0")
```

## Components

### Entity

Now there are some entities:

`BaseEntity` - base entity class for your SQL entities. It has `UUID` id,
`created` and `updated` timestamps, `status` field of `Status` class.

By extending of `BaseEntity` you can create your own entities.

`Status` enum describes possible statuses of an entity:

- `ACTIVE` - active entity
- `NOT_ACTIVE` - entity pending to be active (e.g. user before email
  verification)
- `BLOCKED` - blocked entity
- `DELETED` - deleted entity
- `UNDER_MOBERATION` - entity being under moderation
- `MODERATION_DECLINED` - entity after declined moderation
- `DRAFT` - draft status of an entity

### Criteria

There is `Criteria` class. You can build handy criteria for your domain by
extending this class. Base class have `pageId`, `perPage`, `query` and `status`
params.

### JWT

There are some token types to create JWT tokens:

- `ACCESS` - for access tokens
- `REFRESH` - for refresh tokens
- `ACTIVATION` - for activation tokens,
- `PASSWORD_RESET` - for password reset tokens,
- `CUSTOM` - for any other tokens

### Exceptions

There are some useful exceptions:

- `ResourceNotFoundException` - for not found resources
- `AccessDeniedException` - for authentication exceptions
- `ResourceAlreadyExistsException` - for duplicates
- `DataNotValidException` - for invalid data
- `TokenNotValidException` - for invalid tokens
- `UploadException` - for exceptions during uploads
- `NotEnoughMoneyException` - for insufficient balance

You can use `MessageDto` class for error responding in `ControllerAdvice`.

### Repository

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

There is utility classes for joins in specifications.

### Service

We added `CrudService<Entity, Criteria>` to decrease amount of common code by
entities.

There are some useful methods of CRUD operations:

- `Entity getById(UUID, boolean)` - if `false` it should throw
  `ResourceNotFoundException`
- `Entity getById(UUID)` - `getById(UUID, false)`
- `Page<Entity> getAll(Criteria)` - returns a page of entities corresponding to
  criteria
- `Page<Entity> getAllByAuthorId(UUID, Criteria)` - returns a page of entities
  corresponding to criteria with author's id
- `long countAll(Criteria)` - counts all entities by criteria
- `Entity create(Entity)` - saves an entity to database
- `Entity update(Entity)` - updates an entity in database
- `void detele(UUID)` - marks entity as deleted in database

`Blockable` service provides two additional methods for entities that can be
blocked (e.g. users, posts, comments):

- `void block(UUID)` - marks entity as blocked by id
- `void unblock(UUID)` - marks entity as unblocked by id

`Moderatable` service provides two additional methods for entities that can be
moderated (e.g. posts, comments):

- `void allow(UUID)` - marks entity as allowed (status `ACTIVE`) by id
- `void disallow(UUID)` - marks entity as disallowed (status
  `MODERATION_DECLINED`) by id

### DTOs and Mappers

This library has two main interfaces for mappers.

`Mappable<Entity, DTO>` - mapper for any classes. Provides next methods:

- `Entity toEntity(DTO)`
- `List<Entity> toEntity(List<DTO>)`
- `DTO toDto(Entity)`
- `List<DTO> toDto(List<Entity>)`

`BaseMappable<Entity: BaseEntity, DTO>` extends `Mappable` with ignoring of
`status`, `created` and `updated` fields that can be received from DTO.

You can see some basic interfaces for validation groups:

- `OnPublish` - in case of validation for publishing
- `OnUpdate` - in case of validation for entity updates
- `OnCreate` - in case of validation for entity creates
- `NestedObjectId` - in case of validation for lists of entities where you need
  only ids
- `OnAnyId` - in case of validation with or without id

## License

This library is under [MIT license](./LICENSE).

## Contribution

To contribute, make a fork and open a pull request. You can find
issues [here](https://github.com/ilyalisov/spring-boot-common/issues).

Make sure, you follow project's codestyle.