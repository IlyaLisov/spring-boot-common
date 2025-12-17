package io.github.ilyalisov.common.repository.specification;

import io.github.ilyalisov.common.model.criteria.Criteria;
import org.springframework.data.jpa.domain.Specification;

/**
 * Strategy interface for building JPA {@link Specification} instances based on
 * a given {@link Criteria} object.
 * <p>
 * Implementations of this interface are responsible for translating the
 * filtering parameters encapsulated in a {@code Criteria} instance into a
 * type-safe JPA {@link Specification} that can be used with Spring Data JPA
 * repositories.
 * </p>
 * <p>
 * This abstraction allows decoupling criteria objects from a specific
 * persistence implementation, enabling:
 * <ul>
 *   <li>Reuse of the same {@link Criteria} across different persistence
 *       strategies</li>
 *   <li>Multiple {@link Specification} implementations for the same
 *   criteria</li>
 *   <li>Cleaner separation of concerns between filtering logic and data
 *   access</li>
 * </ul>
 * </p>
 *
 * @param <T> the type of the JPA entity for which the specification is built
 * @param <C> the class of custom criteria for JPA entity
 * @author Ilya Lisov
 * @see Criteria
 * @see Specification
 * @since 0.1.3
 */
public interface SpecBuilder<T, C extends Criteria> {

    /**
     * Builds a JPA {@link Specification} based on the provided
     * {@link Criteria}.
     * <p>
     * The implementation should interpret the values contained in the given
     * criteria instance (such as status, author identifier, search query, or
     * other domain-specific parameters) and compose a corresponding
     * {@link Specification}.
     * </p>
     * <p>
     * Implementations should be null-safe and may return {@code null} if the
     * criteria does not define any filtering constraints, in accordance with
     * Spring Data JPA's {@link Specification} composition semantics.
     * </p>
     *
     * @param criteria the criteria object containing filtering parameters,
     *                 must not be {@code null}
     * @return a {@link Specification} representing all filtering conditions
     * defined by the given criteria, or {@code null} if no constraints
     * are applicable
     */
    Specification<T> build(
            C criteria
    );

}
