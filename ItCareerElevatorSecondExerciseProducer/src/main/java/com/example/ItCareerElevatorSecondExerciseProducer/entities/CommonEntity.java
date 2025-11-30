package com.example.ItCareerElevatorSecondExerciseProducer.entities;

import com.example.ItCareerElevatorSecondExerciseProducer.entities.listeners.CommonEntityListener;
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
}
