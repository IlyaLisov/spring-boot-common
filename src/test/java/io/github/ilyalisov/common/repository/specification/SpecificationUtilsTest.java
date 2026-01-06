package io.github.ilyalisov.common.repository.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SpecificationUtilsTest {

    private Root<Object> root;

    @BeforeEach
    void setUp() {
        root = Mockito.mock(Root.class);
    }

    @Test
    void getJoin_shouldReturnExistingJoin_whenExists() {
        String attribute = "testAttribute";
        JoinType joinType = JoinType.INNER;
        Join<Object, ?> existingJoin = mock(Join.class);
        Attribute attr = mock(Attribute.class);
        when(existingJoin.getJoinType()).thenReturn(joinType);
        when(existingJoin.getAttribute()).thenReturn(attr);
        when(attr.getName()).thenReturn(attribute);
        when(root.getJoins()).thenReturn(Set.of(existingJoin));
        Join<Object, Object> result = SpecificationUtils.getJoin(
                root,
                attribute,
                joinType
        );
        assertEquals(existingJoin, result);
        verify(root, never()).join(anyString(), any(JoinType.class));
    }

    @Test
    void getJoin_shouldCreateNewJoin_whenNotExists() {
        String attribute = "testAttribute";
        JoinType joinType = JoinType.INNER;
        Join<Object, Object> newJoin = mock(Join.class);
        when(root.getJoins()).thenReturn(Collections.emptySet());
        when(root.join(attribute, joinType)).thenReturn(newJoin);
        Join<Object, Object> result = SpecificationUtils.getJoin(
                root,
                attribute,
                joinType
        );
        assertEquals(newJoin, result);
        verify(root).join(attribute, joinType);
    }

    @Test
    void getJoin_shouldCreateNewJoin_whenExistsWithDifferentType() {
        String attribute = "testAttribute";
        JoinType joinType = JoinType.INNER;
        Join<Object, ?> existingJoin = mock(Join.class);
        Attribute attr = mock(Attribute.class);
        Join<Object, Object> newJoin = mock(Join.class);
        when(existingJoin.getJoinType()).thenReturn(JoinType.LEFT);
        when(existingJoin.getAttribute()).thenReturn(attr);
        when(attr.getName()).thenReturn(attribute);
        when(root.getJoins()).thenReturn(Set.of(existingJoin));
        when(root.join(attribute, joinType)).thenReturn(newJoin);
        Join<Object, Object> result = SpecificationUtils.getJoin(
                root,
                attribute,
                joinType
        );
        assertEquals(newJoin, result);
        verify(root).join(attribute, joinType);
    }

}
