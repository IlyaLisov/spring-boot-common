package io.github.ilyalisov.common.web.dto.mapper

import io.github.ilyalisov.common.model.BaseEntity
import org.mapstruct.Mapping

interface BaseMappable<E : BaseEntity, D> : Mappable<E, D> {

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    override fun toEntity(dto: D): E

}