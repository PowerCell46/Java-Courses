package com.ItCareerElevatorFirstExercise.services.implementations;

import com.ItCareerElevatorFirstExercise.services.interfaces.InMemoryStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements InMemoryStorageService {

    private static final Duration ENTRY_TIME_TO_LIVE = Duration.ofDays(1);

    private final ReactiveStringRedisTemplate redis;

    @Override
    public Boolean setValue(String key, String value) {
        return redis.opsForValue()
                .set(key, value, ENTRY_TIME_TO_LIVE).block();
    }

    @Override
    public Optional<String> getByKey(String key) { // ! O(1)
        return redis.opsForValue()
                .get(key).blockOptional();
    }

    @Override
    public Optional<String> getByValue(String value) { // ! O(n)
        return redis.scan()
                .flatMap(key -> redis.opsForValue().get(key)
                .filter(v -> v.equals(value))
                .map(v -> key))
                .next()
                .blockOptional();
    }
}

// ! Using .block() inside a reactive stack is generally discouraged; it breaks the non-blocking model.