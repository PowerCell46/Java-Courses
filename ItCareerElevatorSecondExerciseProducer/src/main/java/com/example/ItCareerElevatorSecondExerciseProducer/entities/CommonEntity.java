package com.example.ItCareerElevatorSecondExerciseProducer.entities;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.util.Base64;

@MappedSuperclass
@EntityListeners(CommonEntityListener.class)
@Getter
@NoArgsConstructor
@Slf4j
public class CommonEntity {

    @Id
    private Long id;

    private static final Base64.Encoder ENCODER = Base64.getUrlEncoder().withoutPadding();
    private static final Base64.Decoder DECODER = Base64.getUrlDecoder();

    private static final Snowflake SNOWFLAKE = IdUtil.getSnowflake(1, 1);

    public String getSnowflakeId() {
        if (id == null) {
            throw new IllegalStateException("Id is null, cannot convert to snowflakeId.");
        }

        byte[] bytes = ByteBuffer.allocate(Long.BYTES).putLong(id).array();
        return ENCODER.encodeToString(bytes);
    }
}
