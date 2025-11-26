package com.ItCareerElevatorFirstExercise.repositories;

import com.ItCareerElevatorFirstExercise.entities.UrlMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UrlMapperRepository extends JpaRepository<UrlMapper, UUID> {

    // * Possible optimization: instead of checking if it exists (1st query) in the DB and then (2nd query) inserting - do it in one query.
    @Query(
            value = """
                    INSERT INTO url_mappers(url, alias, isHttps)
                    VALUES (:url, :alias, :isHttps)
                    ON CONFLICT (url)
                    DO NOTHING
                    RETURNING *
                    """,
            nativeQuery = true
    )
    UrlMapper upsertByUrl(@Param("url") String url, @Param("alias") String alias, @Param("isHttps") Boolean isHttps);

    Optional<UrlMapper> findByURL(String URL);

    Optional<UrlMapper> findByAlias(String alias);
}
