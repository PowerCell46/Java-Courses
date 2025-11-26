package com.ItCareerElevatorFirstExercise.repositories;

import com.ItCareerElevatorFirstExercise.entities.UrlMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlMapperRepository extends JpaRepository<UrlMapper, Long> {

    Optional<UrlMapper> findByURL(String URL);
}
