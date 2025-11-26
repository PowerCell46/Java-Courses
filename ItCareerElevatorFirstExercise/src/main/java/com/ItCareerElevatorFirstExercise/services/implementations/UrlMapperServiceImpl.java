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
                .getByValue(URL); // ! O(n)

        if (optionalInMemoryAlias.isPresent()) {
            log.info("Getting the urlMapper alias from InMemoryStorage.");
            return optionalInMemoryAlias.get();
        }

        Optional<UrlMapper> optionalUrlMapper = urlMapperRepository.findByURL(compressUrl(URL));

        if (optionalUrlMapper.isPresent()) { // Value has expired or was not set properly in memory
            log.info("Getting the urlMapper alias from the Database.");
            setUrlMapperInMemory(optionalUrlMapper.get().getAlias(), URL);

            return optionalUrlMapper.get().getAlias();
        }

        return save(constructUrlMapperFromUrl(URL)).getAlias();
    }

    private static String compressUrl(String URL) {
        final String URL_PREFIX_REGEX = ".*://";

        return URL.replaceFirst(URL_PREFIX_REGEX, "");
    }

    private static String decompressUrl(Boolean isHttps, String URL) {
        return String.format("http%s://%s", isHttps ? "s" : "", URL);
    }

    private void setUrlMapperInMemory(String alias, String URL) {
        log.info("Saving the urlMapper with URL: {} to the InMemoryStorage.", URL);
        inMemoryStorageService.setValue(alias, URL);
    }

    private UrlMapper constructUrlMapperFromUrl(String URL) {
        Boolean isHttps = URL.startsWith("https://");
        URL = compressUrl(URL);
        String snowflakeAlias = SnowflakeUtils.convert(URL);

        return new UrlMapper(URL, snowflakeAlias, isHttps);
    }

    @Override
    public UrlMapper save(UrlMapper urlMapper) {
        log.info("Saving urlMapper with URL: {} to the Database.", urlMapper.getURL());
        urlMapper = urlMapperRepository.save(urlMapper);

        setUrlMapperInMemory(urlMapper.getAlias(), decompressUrl(urlMapper.getIsHttps(), urlMapper.getURL()));

        return urlMapper;
    }

    @Override
    public Optional<String> convertAliasToUrl(String alias) {
        Optional<String> optionalInMemoryUrl = inMemoryStorageService.getByKey(alias); // ! O(1)

        if (optionalInMemoryUrl.isPresent()) {
            log.info("Getting the urlMapper URL from InMemoryStorage.");
            return optionalInMemoryUrl;
        }

        log.info("Making a request to the database to fetch the alias.");
        return urlMapperRepository
                .findByAlias(alias)
                .map(urlMapper -> decompressUrl(urlMapper.getIsHttps(), urlMapper.getURL()));
    }
}
