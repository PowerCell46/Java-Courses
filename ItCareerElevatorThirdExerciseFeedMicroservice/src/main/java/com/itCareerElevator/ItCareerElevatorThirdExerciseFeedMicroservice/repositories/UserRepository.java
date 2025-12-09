package com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.repositories;

import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
