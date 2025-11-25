package com.ItCareerElevatorFirstExercise.services.implementations;

import com.ItCareerElevatorFirstExercise.services.interfaces.InMemoryStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements InMemoryStorageService {

    private final ReactiveStringRedisTemplate redis;

    @Override
    public Boolean setValue(String key, String value) {
        return redis.opsForValue().set(key, value).block(); // TODO: What is block, what is opsForValue
    }

    @Override
    public Optional<String> getValue(String key) {
        return redis.opsForValue().get(key).blockOptional(); // TODO: What is blockOptional, what is opsForValue
    }
}

// ! Using .block() inside a reactive stack is generally discouraged; it breaks the non-blocking model.