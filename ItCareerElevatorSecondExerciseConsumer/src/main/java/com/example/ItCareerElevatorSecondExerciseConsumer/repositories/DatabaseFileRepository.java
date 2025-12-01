package com.example.ItCareerElevatorSecondExerciseConsumer.repositories;

import com.example.ItCareerElevatorSecondExerciseConsumer.entities.DatabaseFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatabaseFileRepository extends JpaRepository<DatabaseFile, Long> {
}
