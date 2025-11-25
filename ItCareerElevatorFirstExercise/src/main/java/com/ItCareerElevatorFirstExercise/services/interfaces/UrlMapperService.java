package com.ItCareerElevatorFirstExercise.services.interfaces;

import com.ItCareerElevatorFirstExercise.entities.UrlMapper;

public interface UrlMapperService {

    UrlMapper save(UrlMapper urlMapper);

    String convertUrlToAlias(String url);
}
