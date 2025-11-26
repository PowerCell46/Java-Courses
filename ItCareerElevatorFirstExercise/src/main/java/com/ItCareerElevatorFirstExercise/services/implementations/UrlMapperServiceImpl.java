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
    public String convertUrlToAlias(String URL) {
        Optional<String> optionalInMemoryAlias = inMemoryStorageService
                .getByValue(normalizeUrl(URL)); // ! O(n)

        if (optionalInMemoryAlias.isPresent()) {
            log.info("Getting the urlMapper alias from InMemoryStorage.");
            return optionalInMemoryAlias.get();
        }

        Optional<UrlMapper> optionalUrlMapper = urlMapperRepository.findByURL(normalizeUrl(URL));

        if (optionalUrlMapper.isPresent()) { // Value has expired or was not set properly in memory
            log.info("Getting the urlMapper alias from the Database.");
            setUrlMapperInMemory(optionalUrlMapper.get().getAlias(), optionalUrlMapper.get().getURL());

            return optionalUrlMapper.get().getAlias();
        }

        return save(constructUrlMapperFromUrl(URL)).getAlias();
    }

    private static String normalizeUrl(String URL) {
        final String URL_PREFIX_REGEX = ".*://";

        return URL.replaceFirst(URL_PREFIX_REGEX, "");
    }

    private void setUrlMapperInMemory(String alias, String URL) {
        log.info("Saving the urlMapper with URL: {} to the InMemoryStorage.", URL);
        inMemoryStorageService.setValue(alias, URL);
    }

    private UrlMapper constructUrlMapperFromUrl(String URL) {
        Boolean isHttps = URL.startsWith("https://");
        URL = normalizeUrl(URL);
        String snowflakeAlias = SnowflakeUtils.convert(URL);

        return new UrlMapper(URL, snowflakeAlias, isHttps);
    }

    @Override
    public UrlMapper save(UrlMapper urlMapper) {
        log.info("Saving urlMapper with URL: {} to the Database.", urlMapper.getURL());
        urlMapper = urlMapperRepository.save(urlMapper);

        setUrlMapperInMemory(urlMapper.getAlias(), urlMapper.getURL());

        return urlMapper;
    }
}
