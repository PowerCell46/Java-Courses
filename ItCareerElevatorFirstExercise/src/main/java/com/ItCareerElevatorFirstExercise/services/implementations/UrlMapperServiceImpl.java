package com.ItCareerElevatorFirstExercise.services.implementations;

import com.ItCareerElevatorFirstExercise.entities.UrlMapper;
import com.ItCareerElevatorFirstExercise.repositories.UrlMapperRepository;
import com.ItCareerElevatorFirstExercise.services.interfaces.UrlMapperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UrlMapperServiceImpl implements UrlMapperService {

    private final UrlMapperRepository urlMapperRepository;

    @Override
    public UrlMapper save(UrlMapper urlMapper) {
        log.info("Saving urlMapper with URL: {}.", urlMapper.getURL());

        return urlMapperRepository.save(urlMapper);
    }
}
