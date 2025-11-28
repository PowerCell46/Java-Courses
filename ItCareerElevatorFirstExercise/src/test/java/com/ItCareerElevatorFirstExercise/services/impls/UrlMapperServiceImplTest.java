package com.ItCareerElevatorFirstExercise.services.impls;

import com.ItCareerElevatorFirstExercise.entities.UrlMapper;
import com.ItCareerElevatorFirstExercise.exceptions.InvalidAliasException;
import com.ItCareerElevatorFirstExercise.exceptions.InvalidSnowflakeIdException;
import com.ItCareerElevatorFirstExercise.repositories.UrlMapperRepository;
import com.ItCareerElevatorFirstExercise.services.implementations.UrlMapperServiceImpl;
import com.ItCareerElevatorFirstExercise.services.interfaces.InMemoryStorageService;
import com.ItCareerElevatorFirstExercise.services.interfaces.SnowflakeIdService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * ExtendWith: Enables Mockito annotations for dependency injection in tests.
 */
@ExtendWith(MockitoExtension.class)
public class UrlMapperServiceImplTest {

    /**
     * Mock: Creates a mock instance of the repository for testing.
     */
    @Mock
    private UrlMapperRepository urlMapperRepository;

    @Mock
    private InMemoryStorageService inMemoryStorageService;

    @Mock
    private SnowflakeIdService snowflakeIdService;

    @InjectMocks
    private UrlMapperServiceImpl urlMapperService;

    private static final String HTTPS_URL = "https://github.com/PowerCell46";

    private static final String HTTP_URL = "http://example.com/test";

    private static final String COMPRESSED_HTTPS_URL = "github.com/PowerCell46";

    private static final String COMPRESSED_HTTP_URL = "example.com/test";

    private static final String ALIAS = "G6tbC7MEwAA";

    private static final String INVALID_ALIAS = "E";

    private static final String EMPTY_ALIAS = "";

    private static final Long SNOWFLAKE_LONG_ID = UrlMapper.convertSnowflakeIdToId(ALIAS);

    // * Test only the public behavior of the class, and let the private methods be covered indirectly through those public methods.
    // - Private methods are implementation details; tests that target them tightly couple your tests to internal structure, making refactors painful.
    // - As long as the public methods behave correctly in all scenarios, it does not matter how the class achieves that internally.
    // - "If you feel a strong need to test a private method directly, it may be a sign that the class is doing too much and should be refactored into smaller units."

    @Nested
    @DisplayName("convertUrlToAlias tests")
    class ConvertUrlToAliasTests {

        @Test
        @DisplayName("Should return alias from in-memory storage when present")
        void testConvertUrlToAliasWithAliasInInMemoryStorage() {
            when(inMemoryStorageService.getByValue(HTTPS_URL)).thenReturn(Optional.of(ALIAS));

            String result = urlMapperService.convertUrlToAlias(HTTPS_URL);

            assertEquals(ALIAS, result);
            // * verify() checks that a mocked method was called with specific arguments
            verify(inMemoryStorageService).getByValue(HTTPS_URL);
            verifyNoInteractions(urlMapperRepository, snowflakeIdService);
        }

        @Test
        @DisplayName("Should return alias from database when missing from in-memory storage")
        void testConvertUrlToAliasWithAliasMissingFromInMemoryStorageButInDatabase() {
            when(inMemoryStorageService.getByValue(HTTPS_URL)).thenReturn(Optional.empty());

            UrlMapper databaseEntity = new UrlMapper(SNOWFLAKE_LONG_ID, COMPRESSED_HTTPS_URL, true);

            when(urlMapperRepository.findByURL(COMPRESSED_HTTPS_URL)).thenReturn(Optional.of(databaseEntity));

            String result = urlMapperService.convertUrlToAlias(HTTPS_URL);

            assertEquals(ALIAS, result);
            verify(urlMapperRepository).findByURL(COMPRESSED_HTTPS_URL);
            verify(inMemoryStorageService).setKeyValuePair(ALIAS, HTTPS_URL);
            verify(snowflakeIdService, never()).generateId();
        }

        @Test
        @DisplayName("Should create new entry when missing from both in-memory storage and database")
        void testConvertUrlToAliasWithAliasMissingFromInMemoryStorageAndFromDatabase() {
            when(inMemoryStorageService.getByValue(HTTPS_URL)).thenReturn(Optional.empty());
            when(urlMapperRepository.findByURL(COMPRESSED_HTTPS_URL)).thenReturn(Optional.empty());
            when(snowflakeIdService.generateId()).thenReturn(SNOWFLAKE_LONG_ID);

            UrlMapper databaseSavedEntity = new UrlMapper(SNOWFLAKE_LONG_ID, COMPRESSED_HTTPS_URL, true);

            when(urlMapperRepository.save(any(UrlMapper.class))).thenReturn(databaseSavedEntity);

            String result = urlMapperService.convertUrlToAlias(HTTPS_URL);

            assertEquals(ALIAS, result);
            verify(snowflakeIdService).generateId();
            verify(urlMapperRepository)
                    .save(argThat(um ->
                            um.getSnowflakeId().equals(ALIAS) &&
                                    um.getURL().equals(COMPRESSED_HTTPS_URL) &&
                                    Boolean.TRUE.equals(um.getIsHttps()))
                    );
            verify(inMemoryStorageService).setKeyValuePair(ALIAS, HTTPS_URL);
        }

        @Test
        @DisplayName("Should handle HTTP URLs correctly (not just HTTPS)")
        void testConvertUrlToAliasWithHttpUrl() {
            // * Entry is missing both from InMemoryStorage and DB
            when(inMemoryStorageService.getByValue(HTTP_URL)).thenReturn(Optional.empty());
            when(urlMapperRepository.findByURL(COMPRESSED_HTTP_URL)).thenReturn(Optional.empty());
            when(snowflakeIdService.generateId()).thenReturn(SNOWFLAKE_LONG_ID);

            UrlMapper databaseSavedEntity = new UrlMapper(SNOWFLAKE_LONG_ID, COMPRESSED_HTTP_URL, false);

            when(urlMapperRepository.save(any(UrlMapper.class))).thenReturn(databaseSavedEntity);

            String result = urlMapperService.convertUrlToAlias(HTTP_URL);

            assertEquals(ALIAS, result);
            verify(urlMapperRepository)
                    .save(argThat(um ->
                            um.getURL().equals(COMPRESSED_HTTP_URL) &&
                                    Boolean.FALSE.equals(um.getIsHttps()))
                    );
        }
    }

    @Nested
    @DisplayName("save tests")
    class SaveTests {

        @Test
        @DisplayName("Should save to database and in-memory storage")
        void testSaveSavesToDbAndToInMemoryStorage() {
            UrlMapper entityToBeSaved = new UrlMapper(SNOWFLAKE_LONG_ID, COMPRESSED_HTTPS_URL, true);
            UrlMapper savedEntity = new UrlMapper(SNOWFLAKE_LONG_ID, COMPRESSED_HTTPS_URL, true);

            when(urlMapperRepository.save(entityToBeSaved)).thenReturn(savedEntity);

            UrlMapper result = urlMapperService.save(entityToBeSaved);

            assertSame(savedEntity, result);
            verify(urlMapperRepository).save(entityToBeSaved);
            verify(inMemoryStorageService).setKeyValuePair(ALIAS, HTTPS_URL);
        }
    }

    @Nested
    @DisplayName("convertAliasToUrl tests")
    class ConvertAliasToUrlTests {

        @Test
        @DisplayName("Should return URL from in-memory storage when present")
        void testConvertAliasToUrlWithUrlInInMemoryStorage() {
            when(inMemoryStorageService.getByKey(ALIAS)).thenReturn(Optional.of(HTTPS_URL));

            Optional<String> result = urlMapperService.convertAliasToUrl(ALIAS);

            assertTrue(result.isPresent());
            assertEquals(HTTPS_URL, result.get());
            verifyNoInteractions(urlMapperRepository);
        }

        @Test
        @DisplayName("Should return URL from database when missing from in-memory storage")
        void testConvertAliasToUrlWithUrlMissingFromInMemoryStorageButInDatabase() {
            when(inMemoryStorageService.getByKey(ALIAS)).thenReturn(Optional.empty());

            UrlMapper databaseResultEntity = new UrlMapper(SNOWFLAKE_LONG_ID, COMPRESSED_HTTPS_URL, true);

            when(urlMapperRepository.findById(SNOWFLAKE_LONG_ID)).thenReturn(Optional.of(databaseResultEntity));

            Optional<String> result = urlMapperService.convertAliasToUrl(ALIAS);

            assertTrue(result.isPresent());
            assertEquals(HTTPS_URL, result.get());
            verify(inMemoryStorageService).setKeyValuePair(ALIAS, HTTPS_URL);
        }

        @Test
        @DisplayName("Should return empty Optional when URL is missing from both in-memory storage and database")
        void testConvertAliasToUrlWithUrlMissingFromInMemoryStorageAndFromDatabase() {
            when(inMemoryStorageService.getByKey(ALIAS)).thenReturn(Optional.empty());

            when(urlMapperRepository.findById(SNOWFLAKE_LONG_ID)).thenReturn(Optional.empty());

            Optional<String> result = urlMapperService.convertAliasToUrl(ALIAS);

            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("Should throw InvalidSnowflakeIdException for invalid alias format")
        void testConvertAliasToUrlWithNonParsableAlias() {
            when(inMemoryStorageService.getByKey(INVALID_ALIAS)).thenReturn(Optional.empty());

            InvalidSnowflakeIdException exception = assertThrows(
                    InvalidSnowflakeIdException.class,
                    () -> urlMapperService.convertAliasToUrl(INVALID_ALIAS)
            );

            assertEquals(String.format("Invalid snowflakeId [%s].", INVALID_ALIAS), exception.getMessage());
        }

        @Test
        @DisplayName("Should throw InvalidAliasException for empty alias")
        void testConvertAliasToUrlWithEmptyAlias() {
            when(inMemoryStorageService.getByKey(EMPTY_ALIAS)).thenReturn(Optional.empty());

            InvalidAliasException exception = assertThrows(
                    InvalidAliasException.class,
                    () -> urlMapperService.convertAliasToUrl(EMPTY_ALIAS)
            );

            assertEquals("Invalid alias [].", exception.getMessage());
        }

        @Test
        @DisplayName("Should handle HTTP URLs correctly when retrieving from database")
        void testConvertAliasToUrlWithHttpUrlFromDatabase() {
            when(inMemoryStorageService.getByKey(ALIAS)).thenReturn(Optional.empty());

            UrlMapper databaseResultEntity = new UrlMapper(SNOWFLAKE_LONG_ID, COMPRESSED_HTTP_URL, false);

            when(urlMapperRepository.findById(SNOWFLAKE_LONG_ID)).thenReturn(Optional.of(databaseResultEntity));

            Optional<String> result = urlMapperService.convertAliasToUrl(ALIAS);

            assertTrue(result.isPresent());
            assertEquals(HTTP_URL, result.get());
            verify(inMemoryStorageService).setKeyValuePair(ALIAS, HTTP_URL);
        }
    }
}
