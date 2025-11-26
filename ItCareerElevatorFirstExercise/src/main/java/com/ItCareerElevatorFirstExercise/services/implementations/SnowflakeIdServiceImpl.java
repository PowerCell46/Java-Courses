package com.ItCareerElevatorFirstExercise.services.implementations;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.ItCareerElevatorFirstExercise.services.interfaces.SnowflakeIdService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SnowflakeIdServiceImpl implements SnowflakeIdService {

    private final Snowflake snowflake;

    public SnowflakeIdServiceImpl(@Value("${snowflake.worker-id:1}") long workerId, @Value("${snowflake.datacenter-id:1}") long datacenterId) {
        this.snowflake = IdUtil.getSnowflake(workerId, datacenterId);
    }

    public Long generateId() {
        return snowflake.nextId();
    }
}
