package com.ItCareerElevatorFirstExercise.services.implementations;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.ItCareerElevatorFirstExercise.services.interfaces.SnowflakeIdService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.Base64;

@Service
public class SnowflakeIdServiceImpl implements SnowflakeIdService {

    private final Snowflake snowflake;

    private static final Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
    private static final Base64.Decoder decoder = Base64.getUrlDecoder();

    public SnowflakeIdServiceImpl(@Value("${snowflake.worker-id:1}") long workerId, @Value("${snowflake.datacenter-id:1}") long datacenterId) {
        this.snowflake = IdUtil.getSnowflake(workerId, datacenterId);
    }

    public Long generateId() {
        return snowflake.nextId();
    }

    public String encodeIdToBase64(Long id) {
        byte[] bytes = ByteBuffer.allocate(8).putLong(id).array(); // Long: 8 bytes

        return encoder.encodeToString(bytes); // 8 bytes: 11-char Base64 (URL-safe, no padding)
    }

    public Long decodeIdFromBase64(String encodedId) {
        byte[] bytes = decoder.decode(encodedId); // 11-char Base64: 8 bytes

        return ByteBuffer.wrap(bytes).getLong(); // 8 bytes: long
    }
}
