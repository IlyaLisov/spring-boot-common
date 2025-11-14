package io.github.ilyalisov.common.web.dto.mapper

interface Mappable<E, D> {

    fun toEntity(dto: D): E

    fun toEntity(dto: List<D>): List<E>

    fun toDto(entity: E): D

    fun toDto(entity: List<E>): List<D>

}