package com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.repositories;

import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.entities.UserFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFeedRepository extends JpaRepository<UserFeed, Long> {
}
