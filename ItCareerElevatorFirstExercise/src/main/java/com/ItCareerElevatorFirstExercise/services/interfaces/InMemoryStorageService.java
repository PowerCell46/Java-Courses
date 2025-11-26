package com.ItCareerElevatorFirstExercise.services.interfaces;

import java.util.Optional;

public interface InMemoryStorageService {

    Boolean setValue(String key, String value);

    Optional<String> getByValue(String value);

    Optional<String> getByKey(String key);
}
