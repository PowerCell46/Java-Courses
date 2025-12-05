package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.implementations;

import cn.hutool.core.lang.Snowflake;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.interfaces.SnowflakeIdGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SnowflakeIdGeneratorServiceImpl implements SnowflakeIdGeneratorService {

    private final Snowflake snowflake;

    @Override
    public long nextId() {
        return snowflake.nextId();
    }
}
