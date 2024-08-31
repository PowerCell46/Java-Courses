package com.mobilele.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@MappedSuperclass
@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
public class BaseTimestamp extends Base {

    @Column
    private LocalDate created;

    @Column
    private LocalDate modified;
}
