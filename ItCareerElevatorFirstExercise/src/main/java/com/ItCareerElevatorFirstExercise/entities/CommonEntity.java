package com.ItCareerElevatorFirstExercise.entities;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.ByteBuffer;
import java.util.Base64;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
            throw new IllegalArgumentException("Cannot convert snowflakeId, because it's null or empty.");
        }

        byte[] bytes;

        try {
            bytes = DECODER.decode(snowflakeId);

        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid Base64 URL-safe snowflake id: " + snowflakeId, ex);
        }

        if (bytes.length != Long.BYTES) {
            throw new IllegalArgumentException(String.format(
                    "Invalid snowflake id length: expected %d  bytes but got %d",
                    Long.BYTES,
                    bytes.length)
            );
        }

        return ByteBuffer.wrap(bytes).getLong();
    }
}
