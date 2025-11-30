package com.example.ItCareerElevatorSecondExerciseProducer.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class LoiMeasurementUnit extends ListOptionItem {

    public static final Long MWh = 1L;

    public static final Long KWh = 2L;

    public LoiMeasurementUnit(String listOptionItemName, Long listOptionItemCode) {
        super(listOptionItemName, listOptionItemCode);
    }
}
