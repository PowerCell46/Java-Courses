package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.entities;

import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.entities.listeners.CommonEntityListener;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.exceptions.InvalidSnowflakeIdException;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.util.Base64;

@MappedSuperclass
@EntityListeners(CommonEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class CommonEntity {

    @Id
    private Long id;

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
