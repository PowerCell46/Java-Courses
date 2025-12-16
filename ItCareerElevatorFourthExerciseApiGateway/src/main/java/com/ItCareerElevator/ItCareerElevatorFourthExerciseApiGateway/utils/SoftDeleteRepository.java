package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.utils;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface SoftDeleteRepository<T, ID> extends JpaRepository<T, ID> {

    List<T> findAllIncludingDeleted();

    Optional<T> findByIdIncludingDeleted(ID id);
}
