package io.github.ilyalisov.common.service;

import io.github.ilyalisov.common.model.BaseEntity;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface CrudService<T extends BaseEntity, C extends Criteria> {

    default T getById(
            final UUID id
    ) {
        return getById(id, false);
    }

    T getById(
            UUID id,
            boolean showAnyway
    );

    Page<T> getAll(
            C criteria
    );

    Page<T> getAllByAuthorId(
            UUID authorId,
            C criteria
    );

    long countAll(
            C criteria
    );

    T create(
            T dto
    );

    T update(
            T dto
    );

    void delete(
            UUID id
    );

}
