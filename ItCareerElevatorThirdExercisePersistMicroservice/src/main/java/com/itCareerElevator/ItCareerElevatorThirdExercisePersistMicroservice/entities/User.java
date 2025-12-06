package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_username_unique", columnList = "username", unique = true)
        }
)
@Getter
@Setter
@NoArgsConstructor
public class User extends CommonEntity {

    @Column(nullable = false)
    private String username;

    @Column(nullable = true)
    private String firstName;

    @Column(nullable = true)
    private String lastName;

    @Column(nullable = true)
    private Boolean isMale;

    @Column(nullable = true)
    private String bio;

    @ManyToOne(fetch = FetchType.EAGER)
    private City city;

    @ManyToOne(fetch = FetchType.EAGER)
    private Country country;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private Set<Tweet> tweets;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_liked_tweets",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tweet_id")
    )
    private Set<Tweet> likedTweets;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_following",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private Set<User> following;

    @ManyToMany(mappedBy = "following", fetch = FetchType.EAGER)
    private Set<User> followers;

    public User(String username) {
        super();

        this.username = username;
        this.tweets = new HashSet<>();
        this.likedTweets = new HashSet<>();
        this.following = new HashSet<>();
        this.followers = new HashSet<>();
    }
}
