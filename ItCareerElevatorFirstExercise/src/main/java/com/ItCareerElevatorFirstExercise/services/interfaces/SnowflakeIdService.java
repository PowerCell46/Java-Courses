package com.ItCareerElevatorFirstExercise.services.interfaces;

public interface SnowflakeIdService {

    Long generateId();

    String encodeIdToBase64(Long id);

    Long decodeIdFromBase64(String encodedId);
}
