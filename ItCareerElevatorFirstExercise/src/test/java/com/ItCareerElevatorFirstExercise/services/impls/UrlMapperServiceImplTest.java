package com.ItCareerElevatorFirstExercise.services.impls;

import com.ItCareerElevatorFirstExercise.entities.CommonEntity;
import com.ItCareerElevatorFirstExercise.entities.UrlMapper;
import com.ItCareerElevatorFirstExercise.exceptions.InvalidAliasException;
import com.ItCareerElevatorFirstExercise.repositories.UrlMapperRepository;
import com.ItCareerElevatorFirstExercise.services.implementations.UrlMapperServiceImpl;
import com.ItCareerElevatorFirstExercise.services.interfaces.InMemoryStorageService;
import com.ItCareerElevatorFirstExercise.services.interfaces.SnowflakeIdService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.longThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UrlMapperServiceImplTest {

    @Mock
    private UrlMapperRepository urlMapperRepository;

    @Mock
    private InMemoryStorageService inMemoryStorageService;

    @Mock
    private SnowflakeIdService snowflakeIdService;

    @InjectMocks
    private UrlMapperServiceImpl urlMapperService;

    private static final String URL = "https://github.com/PowerCell46";

    private static final String COMPRESSED_URL = "github.com/PowerCell46";

    private static final String ALIAS = "G6tbC7MEwAA";

    private static final String INVALID_ALIAS = "E";

    private static final Long SNOWFLAKE_LONG_ID = UrlMapper.convertSnowflakeIdToId(ALIAS);

    // * Test only the public behavior of the class, and let the private methods be covered indirectly through those public methods.
    // - Private methods are implementation details; tests that target them tightly couple your tests to internal structure, making refactors painful.
    // - As long as the public methods behave correctly in all scenarios, it does not matter how the class achieves that internally.
    // - “If you feel a strong need to test a private method directly, it may be a sign that the class is doing too much and should be refactored into smaller units.”

    @Test
    void testConvertUrlToAliasWithAliasInInMemoryStorage() {
        when(inMemoryStorageService.getByValue(URL)).thenReturn(Optional.of(ALIAS));

        String result = urlMapperService.convertUrlToAlias(URL);

        assertEquals(ALIAS, result, "In memory entry should return the correct ALIAS");
        verify(inMemoryStorageService).getByValue(URL);
        verifyNoInteractions(urlMapperRepository);
    }

    @Test
    void testConvertUrlToAliasWithAliasMissingFromInMemoryStorageButInDatabase() {
        when(inMemoryStorageService.getByValue(URL)).thenReturn(Optional.empty());

        UrlMapper databaseEntity = new UrlMapper(SNOWFLAKE_LONG_ID, COMPRESSED_URL, true);

        when(urlMapperRepository.findByURL(COMPRESSED_URL)).thenReturn(Optional.of(databaseEntity));

        String result = urlMapperService.convertUrlToAlias(URL);

        assertEquals(ALIAS, result, "If entry is missing from InMemory but it's present in the DB, it should be returned.");
        verify(urlMapperRepository).findByURL(COMPRESSED_URL);
        verify(inMemoryStorageService).setKeyValuePair(ALIAS, URL);
    }

    @Test
    void testConvertUrlToAliasWithAliasMissingFromInMemoryStorageAndFromDatabase() {
        when(snowflakeIdService.generateId()).thenReturn(SNOWFLAKE_LONG_ID);

        UrlMapper databaseSavedEntity = new UrlMapper(SNOWFLAKE_LONG_ID, COMPRESSED_URL, true);

        when(urlMapperRepository.save(any(UrlMapper.class))).thenReturn(databaseSavedEntity);

        String result = urlMapperService.convertUrlToAlias(URL);

        assertEquals(ALIAS, result, "If entry is missing both from InMemory and DB, it should be saved as a new entry.");
        verify(snowflakeIdService).generateId();
        verify(urlMapperRepository)
                .save(argThat(um ->
                        um.getSnowflakeId().equals(ALIAS) &&
                                um.getURL().equals(COMPRESSED_URL) &&
                                Boolean.TRUE.equals(um.getIsHttps()))
                );
        verify(inMemoryStorageService).setKeyValuePair(ALIAS, URL);
    }

    @Test
    void testSaveSavesToDbAndToInMemoryStorage() {
        UrlMapper entityToBeSaved = new UrlMapper(SNOWFLAKE_LONG_ID, COMPRESSED_URL, true);
        UrlMapper savedEntity = new UrlMapper(SNOWFLAKE_LONG_ID, COMPRESSED_URL, true);

        when(urlMapperRepository.save(entityToBeSaved)).thenReturn(savedEntity);

        UrlMapper result = urlMapperService.save(entityToBeSaved);

        assertSame(savedEntity, result, "Save UrlMapper should save it to the DB and to the InMemoryStorage.");
        verify(urlMapperRepository).save(entityToBeSaved);
        verify(inMemoryStorageService).setKeyValuePair(ALIAS, URL);
    }

    @Test
    void testConvertAliasToUrlWithUrlInInMemoryStorage() {
        when(inMemoryStorageService.getByKey(ALIAS)).thenReturn(Optional.of(URL));

        Optional<String> result = urlMapperService.convertAliasToUrl(ALIAS);

        assertTrue(result.isPresent());
        assertEquals(URL, result.get());
        verifyNoInteractions(urlMapperRepository);
    }

    @Test
    void testConvertAliasToUrlWithUrlMissingFromInMemoryStorageButInDatabase() {
        when(inMemoryStorageService.getByKey(ALIAS)).thenReturn(Optional.empty());

        UrlMapper databaseResultEntity = new UrlMapper(SNOWFLAKE_LONG_ID, COMPRESSED_URL, true);

        when(urlMapperRepository.findById(SNOWFLAKE_LONG_ID)).thenReturn(Optional.of(databaseResultEntity));

        Optional<String> result = urlMapperService.convertAliasToUrl(ALIAS);

        assertTrue(result.isPresent());
        assertEquals(URL, result.get());
        verify(inMemoryStorageService).setKeyValuePair(ALIAS, URL);
    }

    @Test
    void testConvertAliasToUrlWithUrlMissingFromInMemoryStorageAndFromDatabase() {
        when(inMemoryStorageService.getByKey(ALIAS)).thenReturn(Optional.empty());

        when(urlMapperRepository.findById(SNOWFLAKE_LONG_ID)).thenReturn(Optional.empty());

        Optional<String> result = urlMapperService.convertAliasToUrl(ALIAS);

        assertTrue(result.isEmpty());
    }

    @Test
    void testConvertAliasToUrlWithNonParsableAlias() {
        when(inMemoryStorageService.getByKey(ALIAS)).thenReturn(Optional.empty());

        when(UrlMapper.convertSnowflakeIdToId(INVALID_ALIAS)).thenThrow(new IllegalArgumentException("Invalid Base64 URL-safe snowflake id: E"));

        assertThrows(InvalidAliasException.class, () -> urlMapperService.convertAliasToUrl(INVALID_ALIAS));
    }
}
