package com.ItCareerElevatorFirstExercise.services.interfaces;

import java.util.Optional;

public interface InMemoryStorageService {

    Boolean setValue(String key, String value);

    Optional<String> getValue(String key);
}
