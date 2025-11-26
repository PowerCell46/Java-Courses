package com.ItCareerElevatorFirstExercise.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "url_mappers")
@Getter
@Setter
@NoArgsConstructor
public class UrlMapper extends CommonEntity {

    @Column(unique = true, nullable = false)
    private String URL; // Make index on the column

    @Column(nullable = false)
    private Boolean isHttps;

    public UrlMapper(Long snowflakeId, String URL, Boolean isHttps) {
        super(snowflakeId);

        this.URL = URL;
        this.isHttps = isHttps;
    }
}
