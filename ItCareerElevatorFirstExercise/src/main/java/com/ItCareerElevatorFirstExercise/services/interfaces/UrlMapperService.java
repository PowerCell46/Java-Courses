package com.ItCareerElevatorFirstExercise.services.interfaces;

import com.ItCareerElevatorFirstExercise.entities.UrlMapper;

public interface UrlMapperService {

    String convertUrlToAlias(String url);

    UrlMapper save(UrlMapper urlMapper);
}
