package io.github.ilyalisov.common.web.dto.mapper

/**
 * Generic mapper interface for bidirectional conversion between entities and
 * DTOs (Data Transfer Objects). Provides methods for both single object and
 * collection transformations, ensuring consistent mapping patterns across
 * the application.
 *
 * @param E the entity type used for business logic and persistence
 * @param D the DTO type used for data transfer and API responses
 *
 * @author Ilya Lisov
 * @since 0.1.0
 *
 * @see BaseMappable
 */
interface Mappable<E, D> {

    /**
     * Converts a single DTO object to its corresponding entity.
     * Typically used when creating or updating entities from API input.
     *
     * @param dto the DTO object to convert
     * @return the converted entity
     */
    fun toEntity(dto: D): E

    /**
     * Converts a list of DTO objects to a list of entities.
     * Useful for batch operations and collection transformations.
     *
     * @param dto the list of DTO objects to convert
     * @return the list of converted entities
     */
    fun toEntity(dto: List<D>): List<E>

    /**
     * Converts a single entity to its corresponding DTO.
     * Typically used when returning entity data in API responses.
     *
     * @param entity the entity object to convert
     * @return the converted DTO
     */
    fun toDto(entity: E): D

    /**
     * Converts a list of entities to a list of DTOs.
     * Useful for returning collections of data in API responses.
     *
     * @param entity the list of entity objects to convert
     * @return the list of converted DTOs
     */
    fun toDto(entity: List<E>): List<D>

}