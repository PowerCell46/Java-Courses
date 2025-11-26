package com.ItCareerElevatorFirstExercise.services.implementations;

import com.ItCareerElevatorFirstExercise.entities.CommonEntity;
import com.ItCareerElevatorFirstExercise.entities.UrlMapper;
import com.ItCareerElevatorFirstExercise.exceptions.InvalidAliasException;
import com.ItCareerElevatorFirstExercise.repositories.UrlMapperRepository;
import com.ItCareerElevatorFirstExercise.services.interfaces.InMemoryStorageService;
import com.ItCareerElevatorFirstExercise.services.interfaces.SnowflakeIdService;
import com.ItCareerElevatorFirstExercise.services.interfaces.UrlMapperService;
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

    private final SnowflakeIdService snowflakeIdService;

    @Override
    public String convertUrlToAlias(String URL) {
        Optional<String> optionalInMemoryAlias = inMemoryStorageService.getByValue(URL);

        if (optionalInMemoryAlias.isPresent()) {
            log.info("--> Getting the Alias from the InMemoryStorage.");
            return optionalInMemoryAlias.get();
        }

        Optional<UrlMapper> optionalUrlMapper = urlMapperRepository.findByURL(compressUrl(URL));

        if (optionalUrlMapper.isPresent()) { // Value has expired or was not set properly (in the memory storage)
            log.info("--> Getting the Alias from the Database.");

            setUrlMapperInMemory(optionalUrlMapper.get());
            return optionalUrlMapper.get().getSnowflakeId();
        }

        return save(constructNonPersistedUrlMapperFromUrl(URL)).getSnowflakeId();
    }

    private static String compressUrl(String URL) {
        final String URL_PREFIX_REGEX = ".*://";

        return URL.replaceFirst(URL_PREFIX_REGEX, "");
    }

    private static String decompressUrl(UrlMapper urlMapper) {
        return String.format("http%s://%s",
                urlMapper.getIsHttps() ? "s" : "",
                urlMapper.getURL()
        );
    }

    private void setUrlMapperInMemory(UrlMapper urlMapper) {
        String nonCompressedUrl = decompressUrl(urlMapper);

        log.info("*** Persisting urlMapper with URL: {} to the InMemoryStorage.", nonCompressedUrl);
        inMemoryStorageService.setKeyValuePair(urlMapper.getSnowflakeId(), nonCompressedUrl);
    }

    private UrlMapper constructNonPersistedUrlMapperFromUrl(String URL) {
        Boolean isHttps = URL.startsWith("https://");
        String compressedUrl = compressUrl(URL);

        return new UrlMapper(snowflakeIdService.generateId(), compressedUrl, isHttps);
    }

    @Override
    public UrlMapper save(UrlMapper urlMapper) {
        log.info("===> Saving urlMapper with URL: {} to the Database.", urlMapper.getURL());

        urlMapper = urlMapperRepository.save(urlMapper);
        setUrlMapperInMemory(urlMapper);

        return urlMapper;
    }

    @Override
    public Optional<String> convertAliasToUrl(String alias) {
        try {
            Optional<String> optionalInMemoryUrl = inMemoryStorageService.getByKey(alias);

            if (optionalInMemoryUrl.isPresent()) {
                log.info("--> Getting the URL from the InMemoryStorage.");
                return optionalInMemoryUrl;
            }

            Optional<UrlMapper> optionalUrlMapper = urlMapperRepository
                    .findById(CommonEntity.convertSnowflakeIdToId(alias));

            if (optionalUrlMapper.isPresent()) { // Value has expired or was not set properly (in the memory storage)
                log.info("--> Getting the URL from the Database.");
                setUrlMapperInMemory(optionalUrlMapper.get());
            }

            return optionalUrlMapper.map(UrlMapperServiceImpl::decompressUrl);

        } catch (IllegalArgumentException | IllegalStateException e) {
            log.error("Error occurred with alias: {}{}\t{}", alias, System.lineSeparator(), e.getMessage());
            throw new InvalidAliasException(String.format("Invalid alias [%s].", alias));
        }
    }
}
