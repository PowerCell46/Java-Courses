package com.ItCareerElevatorFirstExercise.services.implementations;

import com.ItCareerElevatorFirstExercise.entities.UrlMapper;
import com.ItCareerElevatorFirstExercise.repositories.UrlMapperRepository;
import com.ItCareerElevatorFirstExercise.services.interfaces.InMemoryStorageService;
import com.ItCareerElevatorFirstExercise.services.interfaces.UrlMapperService;
import com.ItCareerElevatorFirstExercise.utils.SnowflakeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UrlMapperServiceImpl implements UrlMapperService {

    private final UrlMapperRepository urlMapperRepository;
    private final InMemoryStorageService inMemoryStorageService;

    @Override
    public UrlMapper save(UrlMapper urlMapper) {
        log.info("Saving urlMapper with URL: {}.", urlMapper.getURL());

        return urlMapperRepository.save(urlMapper);
    }

    @Override
    public String convertUrlToAlias(String URL) {
        Optional<String> optionalInMemory = inMemoryStorageService
                .getValue(URL.replaceFirst(".*://", ""));

        if (optionalInMemory.isPresent()) {
            log.info("Getting the urlMapper alias from InMemoryStorage.");
            return optionalInMemory.get();
        }
        // * 1. First check if it's present in the database, don't rush to construct and create it
        return save(constructUrlMapper(URL)).getAlias();
    }

    private UrlMapper constructUrlMapper(String URL) {
        Boolean isHttps = URL.startsWith("https://");
        URL = URL.replaceFirst(".*://", ""); // * 2. This repeats to times. It's not OK.
        String alias = SnowflakeUtils.convert(URL);

        inMemoryStorageService.setValue(URL, alias);

        return new UrlMapper(URL, alias, isHttps);
    }
}
