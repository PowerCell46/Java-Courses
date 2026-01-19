package com.ItCareerElevatorSixthExercise.entities;

import com.ItCareerElevatorSixthExercise.entities.listeners.CommonEntityListener;
import com.ItCareerElevatorSixthExercise.exceptions.InvalidSnowflakeIdException;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.Base64;

@Slf4j
@Getter
@Setter
@MappedSuperclass
@EntityListeners(CommonEntityListener.class)
public class CommonEntity {

    @Id
    private Long id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // activeFrom

    @Column(nullable = false)
    private LocalDateTime lastModifiedAt;

    public CommonEntity() {
        this.createdAt = LocalDateTime.now();
        this.lastModifiedAt = LocalDateTime.now();
    }

    private static final Base64.Encoder ENCODER = Base64.getUrlEncoder().withoutPadding();
    private static final Base64.Decoder DECODER = Base64.getUrlDecoder();

    public String getSnowflakeId() {
        if (id == null) {
            throw new IllegalStateException("Id is null, cannot convert to snowflakeId.");
        }

        byte[] bytes = ByteBuffer.allocate(Long.BYTES).putLong(id).array();
        return ENCODER.encodeToString(bytes);
    }

    public static Long convertSnowflakeIdToId(String snowflakeId) {
        if (snowflakeId == null || snowflakeId.isEmpty()) {
            log.error("Cannot convert snowflakeId, because it's null or empty.");
            throw new InvalidSnowflakeIdException(String.format("Invalid snowflakeId %s.", snowflakeId));
        }

        byte[] bytes;

        try {
            bytes = DECODER.decode(snowflakeId);

        } catch (IllegalArgumentException ex) {
            log.error("Invalid Base64 URL-safe snowflake id: {}.", snowflakeId);
            throw new InvalidSnowflakeIdException(String.format("Invalid snowflakeId %s.", snowflakeId));
        }

        if (bytes.length != Long.BYTES) {
            log.error("Invalid snowflake id length: expected {} bytes but got {}.", Long.BYTES, bytes.length);
            throw new InvalidSnowflakeIdException(String.format("Invalid snowflakeId %s.", snowflakeId));
        }

        return ByteBuffer.wrap(bytes).getLong();
    }

    @PrePersist // * Called once before the entity is first saved (INSERT)
    public void prePersist() {
        this.lastModifiedAt = LocalDateTime.now();
    }

    @PreUpdate // * Called every time the entity is updated (UPDATE)
    public void preUpdate() {
        this.lastModifiedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommonEntity that = (CommonEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
