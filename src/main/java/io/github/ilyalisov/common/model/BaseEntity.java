package io.github.ilyalisov.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Abstract base entity class that provides common fields and behavior for all
 * JPA entities in the system. This class implements serializable and includes
 * automatic ID generation, audit timestamps, and status tracking.
 * <p>
 * Entities extending this class will automatically have:
 * <ul>
 * <li>UUID primary key with automatic generation</li>
 * <li>Creation and update timestamps</li>
 * <li>Status field with default ACTIVE status</li>
 * <li>Proper equals/hashCode implementation based on ID</li>
 * <li>JSON serialization formatting for timestamps</li>
 * </ul>
 * </p>
 *
 * @author Ilya Lisov
 * @see Serializable
 * @see Status
 * @see MappedSuperclass
 * @since 0.1.0
 */
@MappedSuperclass
@Getter
@Setter
@ToString
public abstract class BaseEntity implements Serializable {

    /**
     * Universally unique identifier serving as the primary key for the entity.
     * Automatically generated using UUID strategy and stored as VARCHAR in
     * database for better compatibility across different database systems.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    protected UUID id;

    /**
     * Timestamp indicating when the entity was initially created.
     * This field is automatically set upon entity creation and cannot be
     * updated afterward. Formatted in ISO-like pattern for JSON serialization.
     */
    @Column(updatable = false)
    @CreationTimestamp
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"
    )
    protected LocalDateTime created;

    /**
     * Timestamp indicating the last time the entity was updated.
     * This field is automatically updated by Hibernate on each entity update.
     * Formatted in ISO-like pattern for JSON serialization.
     */
    @UpdateTimestamp
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"
    )
    protected LocalDateTime updated;

    /**
     * Current status of the entity indicating its lifecycle state.
     * Defaults to ACTIVE upon entity creation. Stored as string in database
     * for better readability and maintainability.
     */
    @Enumerated(EnumType.STRING)
    protected Status status;

    /**
     * Default constructor that initializes the entity with default values.
     * Sets status to ACTIVE and initializes timestamps to current time.
     */
    public BaseEntity() {
        this.status = Status.ACTIVE;
        this.created = LocalDateTime.now();
        this.updated = LocalDateTime.now();
    }

    /**
     * Compares this entity with another object for equality.
     * Two entities are considered equal if they have the same non-null ID
     * and are of the same class. This implementation provides consistent
     * behavior for JPA entity comparisons.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(
            final Object o
    ) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseEntity that = (BaseEntity) o;
        return id.equals(that.id);
    }

    /**
     * Returns a hash code value for the entity based on its ID.
     * This implementation ensures that entities with the same ID have
     * the same hash code, maintaining the equals-hashCode contract.
     *
     * @return a hash code value for this entity
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
