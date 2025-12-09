package com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.repositories;

import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.entities.UserFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserFeedRepository extends JpaRepository<UserFeed, Long> {

    Set<UserFeed> findAllByHasBeenSeen(Boolean hasBeenSeen);

    List<UserFeed> findAllByUserIdAndHasBeenSeenOrderByLastModifiedAtDesc(Long userId, Boolean hasBeenSeen);
}
