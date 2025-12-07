package com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "tweets",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"content", "created_by_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Tweet extends CommonEntity {

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    private User createdBy;

    @ManyToMany(mappedBy = "likedTweets")
    private Set<User> likedBy;

    public Tweet(String content, User createdBy) {
        super();
        this.content = content;
        this.createdBy = createdBy;
        this.likedBy = new HashSet<>();
    }

    public Tweet(String content, User createdBy, Set<User> likedBy) {
        super();
        this.content = content;
        this.createdBy = createdBy;
        this.likedBy = likedBy;
    }
}
