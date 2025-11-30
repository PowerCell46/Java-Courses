package com.example.ItCareerElevatorSecondExerciseProducer.repositories;

import com.example.ItCareerElevatorSecondExerciseProducer.entities.LoiDocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoiDocumentTypeRepository extends JpaRepository<LoiDocumentType, Long> {

    Optional<LoiDocumentType> findByListOptionItemCode(Long code);
}
