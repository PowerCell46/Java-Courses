package com.ItCareerElevatorFirstExercise.utils;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicLong;


public class SnowflakeUtils {

    private static final long CUSTOM_EPOCH = 1288834974657L;

    private static final int WORKER_ID_BITS = 10;

    private static final int SEQUENCE_BITS = 12;

    private static final int TIMESTAMP_SHIFT = WORKER_ID_BITS + SEQUENCE_BITS; // 22

    private final AtomicLong sequence = new AtomicLong(0);

    public String convert(String value) {
        final String UNIQUE_SEED = "PowerCell46"; // TODO: ENV VAR?

        return toBase64String(generateSnowflakeId(UNIQUE_SEED));
    }

    public String toBase64String(long id) {
        ByteBuffer buffer = ByteBuffer.allocate(8);

        buffer.putLong(0, id);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(buffer.array());
    }

    public long generateSnowflakeId(String workerIdSeed) {
        long timestamp = System.currentTimeMillis() - CUSTOM_EPOCH;

        long workerId = Math.abs(workerIdSeed.hashCode() % (1 << WORKER_ID_BITS)); // Max 1023

        long sequenceNum = sequence.getAndIncrement() % (1 << SEQUENCE_BITS);

        long id = (timestamp << TIMESTAMP_SHIFT) | (workerId << SEQUENCE_BITS) | sequenceNum;

        return id;
    }
}
