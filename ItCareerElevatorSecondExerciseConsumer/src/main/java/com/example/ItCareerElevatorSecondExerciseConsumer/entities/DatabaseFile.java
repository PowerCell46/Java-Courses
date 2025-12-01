package com.example.ItCareerElevatorSecondExerciseConsumer.entities;

import com.example.ItCareerElevatorSecondExerciseConsumer.exceptions.InvalidSnowflakeIdException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.util.Base64;

@Entity
@Table(name = "database_files")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class DatabaseFile {

    @Id
    private Long id;

    @Column
    private byte[] content;

    @Column
    private String contentType;

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
            throw new InvalidSnowflakeIdException(String.format("Invalid snowflakeId [%s].", snowflakeId));
        }

        byte[] bytes;

        try {
            bytes = DECODER.decode(snowflakeId);

        } catch (IllegalArgumentException ex) {
            log.error("Invalid Base64 URL-safe snowflake id: {}", snowflakeId);
            throw new InvalidSnowflakeIdException(String.format("Invalid snowflakeId [%s].", snowflakeId));
        }

        if (bytes.length != Long.BYTES) {
            log.error("Invalid snowflake id length: expected {}  bytes but got {}", Long.BYTES, bytes.length);
            throw new InvalidSnowflakeIdException(String.format("Invalid snowflakeId [%s].", snowflakeId));
        }

        return ByteBuffer.wrap(bytes).getLong();
    }
}
