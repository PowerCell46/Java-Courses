package com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.repositories;

import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.entities.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
    List<Tweet> findAllByCreatedByUsernameOrderByLastModifiedAtDesc(String username);
}
