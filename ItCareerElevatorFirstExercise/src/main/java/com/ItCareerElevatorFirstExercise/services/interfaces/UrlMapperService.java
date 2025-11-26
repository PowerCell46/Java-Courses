package com.ItCareerElevatorFirstExercise.services.interfaces;

import com.ItCareerElevatorFirstExercise.entities.UrlMapper;

import java.util.Optional;

public interface UrlMapperService {

    String convertUrlToAlias(String url);

    UrlMapper save(UrlMapper urlMapper);

    Optional<String> convertAliasToUrl(String alias);
}
