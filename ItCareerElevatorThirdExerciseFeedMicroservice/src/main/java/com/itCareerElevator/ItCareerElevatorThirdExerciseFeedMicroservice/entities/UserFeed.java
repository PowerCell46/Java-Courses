package com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_feeds")
@Getter
@Setter
@NoArgsConstructor
public class UserFeed extends CommonEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Tweet tweet;

    private Boolean hasBeenSeen;

    public UserFeed(User user, Tweet tweet) {
        this.user = user;
        this.tweet = tweet;
        this.hasBeenSeen = false;
    }
}
