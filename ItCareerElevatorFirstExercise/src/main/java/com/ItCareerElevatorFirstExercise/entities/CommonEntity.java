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

    private static final Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
    private static final Base64.Decoder decoder = Base64.getUrlDecoder();

    public String getSnowflakeId() {
        final int LONG_BYTES = Long.BYTES;

        byte[] bytes = ByteBuffer.allocate(LONG_BYTES).putLong(id).array();

        return encoder.encodeToString(bytes); // 8 bytes: 11-char Base64 (URL-safe, no padding)
    }

    public static Long convertSnowflakeIdToId(String encodedId) {
        byte[] bytes = decoder.decode(encodedId); // 11-char Base64: 8 bytes

        return ByteBuffer.wrap(bytes).getLong(); // 8 bytes: long
    }
}
