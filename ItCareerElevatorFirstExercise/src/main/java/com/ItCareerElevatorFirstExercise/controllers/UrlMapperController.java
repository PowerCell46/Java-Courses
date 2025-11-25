package com.ItCareerElevatorFirstExercise.controllers;

import com.ItCareerElevatorFirstExercise.services.interfaces.UrlMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/url-mapper/")
@RequiredArgsConstructor
public class UrlMapperController {

    private final UrlMapperService urlMapperService;
}
