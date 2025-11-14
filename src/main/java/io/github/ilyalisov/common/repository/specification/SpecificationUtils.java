package io.github.ilyalisov.common.repository.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public class SpecificationUtils {

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
