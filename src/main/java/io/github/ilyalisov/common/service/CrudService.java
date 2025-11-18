package io.github.ilyalisov.common.service;

import io.github.ilyalisov.common.model.BaseEntity;
import io.github.ilyalisov.common.model.criteria.Criteria;
import org.springframework.data.domain.Page;

import java.util.UUID;

/**
 * Generic CRUD service interface providing common create, read, update, and
 * delete operations for entities. This interface serves as a foundation for
 * service layers across different domain entities, ensuring consistent
 * behavior and reducing boilerplate code.
 *
 * @param <T> the entity type extending BaseEntity
 * @param <C> the criteria type used for filtering and pagination
 * @author Ilya Lisov
 * @see BaseEntity
 * @see Criteria
 * @since 0.1.0
 */
public interface CrudService<T extends BaseEntity, C extends Criteria> {

    /**
     * Retrieves an entity by its ID. This default implementation delegates
     * to the overloaded method with safe mode set to false, meaning it will
     * throw an exception if the entity is not found or not accessible.
     *
     * @param id the UUID of the entity to retrieve
     * @return the found entity
     * @throws io.github.ilyalisov.common.model.ResourceNotFoundException
     * if the entity is not found or not accessible with normal visibility rules
     */
    default T getById(
            final UUID id
    ) {
        return getById(id, false);
    }

    /**
     * Retrieves an entity by its ID with optional safe mode. When safe mode
     * is disabled, enforces normal visibility rules (e.g., only active
     * entities). When safe mode is enabled, may return entities regardless
     * of their status or visibility.
     *
     * @param id         the UUID of the entity to retrieve
     * @param showAnyway if true, bypasses normal visibility rules and
     *                   status filters
     * @return the found entity
     * @throws io.github.ilyalisov.common.model.ResourceNotFoundException
     * if the entity is not found
     */
    T getById(
            UUID id,
            boolean showAnyway
    );

    /**
     * Retrieves a paginated list of entities matching the provided criteria.
     * The criteria includes pagination parameters, status filters, search
     * queries, and other domain-specific filtering options.
     *
     * @param criteria the filtering and pagination criteria
     * @return a page of entities matching the criteria
     */
    Page<T> getAll(
            C criteria
    );

    /**
     * Counts the number of entities matching the provided criteria. This
     * method is optimized for counting without loading actual entity data,
     * making it efficient for statistics and pagination metadata.
     *
     * @param criteria the filtering criteria
     * @return the total count of entities matching the criteria
     */
    long countAll(
            C criteria
    );

    /**
     * Creates a new entity from the provided data. Validates the input,
     * applies business rules, and persists the entity to the database.
     *
     * @param dto the entity data to create
     * @return the created entity with generated fields (ID, timestamps)
     * @throws io.github.ilyalisov.common.model.ResourceAlreadyExistsException
     * if an entity with conflicting unique constraints exists
     * @throws io.github.ilyalisov.common.model.DataNotValidException
     * if the input data fails validation
     */
    T create(
            T dto
    );

    /**
     * Updates an existing entity with the provided data. The entity must
     * exist and be accessible. Only modifiable fields are updated while
     * preserving audit fields and immutable properties.
     *
     * @param dto the entity data to update
     * @return the updated entity
     * @throws io.github.ilyalisov.common.model.ResourceNotFoundException
     * if the entity to update is not found
     * @throws io.github.ilyalisov.common.model.DataNotValidException
     * if the input data fails validation
     */
    T update(
            T dto
    );

    /**
     * Deletes an entity by its ID. Typically, implements soft delete by
     * updating the entity's status to DELETED rather than physical removal
     * from the database, preserving data integrity and audit history.
     *
     * @param id the UUID of the entity to delete
     * @throws io.github.ilyalisov.common.model.ResourceNotFoundException
     * if the entity to delete is not found
     */
    void delete(
            UUID id
    );

}
