package com.example.ItCareerElevatorSecondExerciseProducer.repositories;

import com.example.ItCareerElevatorSecondExerciseProducer.entities.ElectricityInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectricityInvoiceRepository extends JpaRepository<ElectricityInvoice, Long> {
}
