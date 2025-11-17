package io.github.ilyalisov.common.web.dto.mapper

import io.github.ilyalisov.common.model.BaseEntity
import org.mapstruct.Mapping

/**
 * Enhanced mapper interface that extends [Mappable] with specific
 * ignore rules for BaseEntity audit fields. This interface ensures that
 * status and timestamp fields are not overwritten during DTO to entity
 * mapping, preserving important audit information.
 *
 * @param E the entity type extending BaseEntity
 * @param D the DTO type used for data transfer
 *
 * @author Ilya Lisov
 * @since 0.1.0
 *
 * @see Mappable
 * @see BaseEntity
 */
interface BaseMappable<E : BaseEntity, D> : Mappable<E, D> {

    /**
     * Converts a DTO to an entity while ignoring audit fields that should
     * not be modified from DTO data. Specifically ignores:
     * - status (to preserve entity lifecycle state)
     * - created (to maintain creation timestamp integrity)
     * - updated (to allow automatic timestamp updates)
     *
     * @param dto the DTO object to convert
     * @return the entity with DTO data mapped, excluding audit fields
     */
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    override fun toEntity(dto: D): E

}