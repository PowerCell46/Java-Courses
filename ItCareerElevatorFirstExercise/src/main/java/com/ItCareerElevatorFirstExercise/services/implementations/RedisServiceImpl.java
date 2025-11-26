package com.ItCareerElevatorFirstExercise.services.implementations;

import com.ItCareerElevatorFirstExercise.services.interfaces.InMemoryStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisServiceImpl implements InMemoryStorageService {

    private static final Duration ENTRY_TIME_TO_LIVE = Duration.ofDays(1);
    private static final String VALUE_KEY_PREFIX = "v:";

    private final StringRedisTemplate redis;

    @Override
    public Boolean setKeyValuePair(String key, String value) {
        String valueAsKey = VALUE_KEY_PREFIX + value;

        try {
            // * key -> value
            redis.opsForValue().set(key, value, ENTRY_TIME_TO_LIVE);

            // * (prefixed) value -> key
            redis.opsForValue().set(valueAsKey, key, ENTRY_TIME_TO_LIVE);

            return true;

        } catch (Exception e) {
            log.error("Failed to store Redis key-value pair. key='{}', value='{}'", key, value, e);
            return false;
        }
    }

    @Override
    public Optional<String> getByKey(String key) {
        String result = redis.opsForValue().get(key);

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<String> getByValue(String value) {
        String valueAsKey = VALUE_KEY_PREFIX + value;

        String result = redis.opsForValue().get(valueAsKey);
        return Optional.ofNullable(result);
    }
}

// ! Using .block() inside a reactive stack is generally discouraged; it breaks the non-blocking model.