package io.github.ilyalisov.common.repository.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

/**
 * Utility class providing helper methods for working with JPA Specifications
 * and Criteria API. Contains reusable functionality for managing joins and
 * other specification-related operations.
 *
 * @author Ilya Lisov
 * @since 0.1.0
 */
public class SpecificationUtils {

    /**
     * Retrieves an existing join or creates a new one if it doesn't exist.
     * This method prevents duplicate joins in criteria queries by checking
     * for existing joins with the same attribute and join type before
     * creating a new one.
     *
     * @param <T1>      the source entity type
     * @param <T2>      the target entity type to join with
     * @param root      the root entity in the criteria query
     * @param attribute the name of the attribute to join on
     * @param joinType  the type of join to perform (INNER, LEFT, RIGHT, etc.)
     * @return an existing join if found, or a new join if not present
     * @throws IllegalArgumentException if the attribute does not exist on
     *                                  the root entity or if the join cannot
     *                                  be created
     */
    public static <T1, T2> Join<T1, T2> getJoin(
            final Root<T1> root,
            final String attribute,
            final JoinType joinType
    ) {
        for (Join<T1, ?> join : root.getJoins()) {
            if (join.getJoinType() == joinType
                    && join.getAttribute().getName().equals(attribute)) {
                return (Join<T1, T2>) join;
            }
        }
        return root.join(attribute, joinType);
    }

}
