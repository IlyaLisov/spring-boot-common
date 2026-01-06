package io.github.ilyalisov.common.repository.specification

import io.github.ilyalisov.common.model.BaseEntity
import io.github.ilyalisov.common.model.Status
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Path
import jakarta.persistence.criteria.Root
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

class CommonSpecTest {

    private lateinit var root: Root<BaseEntity>
    private lateinit var query: CriteriaQuery<*>
    private lateinit var cb: CriteriaBuilder

    @BeforeEach
    fun setUp() {
        root = mock(Root::class.java) as Root<BaseEntity>
        query = mock(CriteriaQuery::class.java)
        cb = mock(CriteriaBuilder::class.java)
    }

    @Test
    fun `hasStatus should create specification for given status`() {
        val status = Status.ACTIVE
        val path = mock(Path::class.java) as Path<Status>
        `when`(root.get<Status>("status")).thenReturn(path)

        val spec = CommonSpec.hasStatus<BaseEntity>(status)
        spec.toPredicate(root, query, cb)

        verify(cb).equal(path, status)
    }

    @Test
    fun `isActive should create specification for ACTIVE status`() {
        val path = mock(Path::class.java) as Path<Status>
        `when`(root.get<Status>("status")).thenReturn(path)

        val spec = CommonSpec.isActive<BaseEntity>()
        spec.toPredicate(root, query, cb)

        verify(cb).equal(path, Status.ACTIVE)
    }

    @Test
    fun `isNotActive should create specification for NOT_ACTIVE status`() {
        val path = mock(Path::class.java) as Path<Status>
        `when`(root.get<Status>("status")).thenReturn(path)

        val spec = CommonSpec.isNotActive<BaseEntity>()
        spec.toPredicate(root, query, cb)

        verify(cb).equal(path, Status.NOT_ACTIVE)
    }

    @Test
    fun `isBlocked should create specification for BLOCKED status`() {
        val path = mock(Path::class.java) as Path<Status>
        `when`(root.get<Status>("status")).thenReturn(path)

        val spec = CommonSpec.isBlocked<BaseEntity>()
        spec.toPredicate(root, query, cb)

        verify(cb).equal(path, Status.BLOCKED)
    }

    @Test
    fun `isDeleted should create specification for DELETED status`() {
        val path = mock(Path::class.java) as Path<Status>
        `when`(root.get<Status>("status")).thenReturn(path)

        val spec = CommonSpec.isDeleted<BaseEntity>()
        spec.toPredicate(root, query, cb)

        verify(cb).equal(path, Status.DELETED)
    }

    @Test
    fun `isUnderModeration should create specification for UNDER_MODERATION status`() {
        val path = mock(Path::class.java) as Path<Status>
        `when`(root.get<Status>("status")).thenReturn(path)

        val spec = CommonSpec.isUnderModeration<BaseEntity>()
        spec.toPredicate(root, query, cb)

        verify(cb).equal(path, Status.UNDER_MODERATION)
    }

    @Test
    fun `isModerationDeclined should create specification for MODERATION_DECLINED status`() {
        val path = mock(Path::class.java) as Path<Status>
        `when`(root.get<Status>("status")).thenReturn(path)

        val spec = CommonSpec.isModerationDeclined<BaseEntity>()
        spec.toPredicate(root, query, cb)

        verify(cb).equal(path, Status.MODERATION_DECLINED)
    }

    @Test
    fun `isDraft should create specification for DRAFT status`() {
        val path = mock(Path::class.java) as Path<Status>
        `when`(root.get<Status>("status")).thenReturn(path)

        val spec = CommonSpec.isDraft<BaseEntity>()
        spec.toPredicate(root, query, cb)

        verify(cb).equal(path, Status.DRAFT)
    }

    @Test
    fun `hasAuthorId should create specification for given author id`() {
        val authorId = UUID.randomUUID()
        val authorPath = mock(Path::class.java) as Path<Any>
        val idPath = mock(Path::class.java) as Path<UUID>

        `when`(root.get<Any>("author")).thenReturn(authorPath)
        `when`(authorPath.get<UUID>("id")).thenReturn(idPath)

        val spec = CommonSpec.hasAuthorId<BaseEntity>(authorId)
        spec.toPredicate(root, query, cb)

        verify(cb).equal(idPath, authorId)
    }

    @Test
    fun `hasAuthorId should return conjunction if id is null`() {
        val spec = CommonSpec.hasAuthorId<BaseEntity>(null)
        spec.toPredicate(root, query, cb)

        verify(cb).conjunction()
    }

    @Test
    fun `inPeriod should create specification for date range`() {
        val start = Date()
        val end = Date()
        val createdAtPath = mock(Path::class.java) as Path<Date>
        `when`(root.get<Date>("created")).thenReturn(createdAtPath)

        val spec = CommonSpec.inPeriod<BaseEntity, Date>(start, end)
        spec.toPredicate(root, query, cb)

        verify(cb).between(createdAtPath, start, end)
    }

    @Test
    fun `inPeriod should create specification for start date only`() {
        val start = Date()
        val createdAtPath = mock(Path::class.java) as Path<Date>
        `when`(root.get<Date>("created")).thenReturn(createdAtPath)

        val spec = CommonSpec.inPeriod<BaseEntity, Date>(start, null)
        spec.toPredicate(root, query, cb)

        verify(cb).greaterThanOrEqualTo(createdAtPath, start)
    }

    @Test
    fun `inPeriod should create specification for end date only`() {
        val end = Date()
        val createdAtPath = mock(Path::class.java) as Path<Date>
        `when`(root.get<Date>("created")).thenReturn(createdAtPath)

        val spec = CommonSpec.inPeriod<BaseEntity, Date>(null, end)
        spec.toPredicate(root, query, cb)

        verify(cb).lessThanOrEqualTo(createdAtPath, end)
    }

    @Test
    fun `inPeriod should return conjunction if both dates are null`() {
        val spec = CommonSpec.inPeriod<BaseEntity, Date>(null, null)
        spec.toPredicate(root, query, cb)

        verify(cb).conjunction()
    }

    @Test
    fun `activeInPeriod should combine isActive and inPeriod`() {
        val start = Date()
        val end = Date()
        val statusPath = mock(Path::class.java) as Path<Status>
        val createdAtPath = mock(Path::class.java) as Path<Date>

        `when`(root.get<Status>("status")).thenReturn(statusPath)
        `when`(root.get<Date>("created")).thenReturn(createdAtPath)

        val spec = CommonSpec.activeInPeriod<BaseEntity, Date>(start, end)
        spec.toPredicate(root, query, cb)

        verify(cb).equal(statusPath, Status.ACTIVE)
        verify(cb).between(createdAtPath, start, end)
    }

}
