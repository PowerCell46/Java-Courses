package com.ItCareerElevatorFirstExercise.repositories;

import com.ItCareerElevatorFirstExercise.entities.UrlMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UrlMapperRepository extends JpaRepository<UrlMapper, UUID> {

}
