package com.example.ItCareerElevatorSecondExerciseProducer.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class LoiDocumentType extends ListOptionItem {

    public static final Long INVOICE = 1L;

    public static final Long DEBIT_NOTE = 2L;

    public static final Long CREDIT_NOTE = 3L;

    public LoiDocumentType(String listOptionItemName, Long listOptionItemCode) {
        super(listOptionItemName, listOptionItemCode);
    }
}
