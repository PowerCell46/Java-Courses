package com.ItCareerElevatorFirstExercise.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "url_mappers", indexes = {
        @Index(name = "idx_url_unique", columnList = "url", unique = true)
})
@Getter
@Setter
@NoArgsConstructor
public class UrlMapper extends CommonEntity {

    @Column(nullable = false)
    private String URL;

    @Column(nullable = false)
    private Boolean isHttps;

    public UrlMapper(Long id, String URL, Boolean isHttps) {
        super(id);

        this.URL = URL;
        this.isHttps = isHttps;
    }
}
