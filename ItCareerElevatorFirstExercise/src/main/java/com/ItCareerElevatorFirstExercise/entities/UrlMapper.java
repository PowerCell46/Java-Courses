package com.ItCareerElevatorFirstExercise.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Entity
@Table(name = "url_mappers")
@Getter
@Setter
@NoArgsConstructor
public class UrlMapper {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // TODO: Change to Snowflake id
    private UUID id;

    @Column(unique = true, nullable = false)
    private String alias;

    @Column(nullable = false)
    private String URL;

    @Column(nullable = false)
    private Boolean isHttps;

    public UrlMapper(String URL, String alias, Boolean isHttps) {
        this.URL = URL;
        this.alias = alias;
        this.isHttps = isHttps;
    }
}
