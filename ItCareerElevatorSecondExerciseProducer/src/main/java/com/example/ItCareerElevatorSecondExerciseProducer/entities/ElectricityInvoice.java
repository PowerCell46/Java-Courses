package com.example.ItCareerElevatorSecondExerciseProducer.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "electricity_invoices")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ElectricityInvoice extends CommonEntity {

    @Column
    private String accessPoint;

    @Column
    private String invoiceNumber;

    @Column
    private LocalDate periodFrom;

    @Column
    private LocalDate periodTo;

    @Column(precision = 15, scale = 3)
    private BigDecimal totalQuantity;

    @Column(precision = 15, scale = 2)
    private BigDecimal singlePrice;

    @Column(precision = 15, scale = 2)
    private BigDecimal totalSum;

    @ManyToOne
    private LoiDocumentType loiDocumentType;

    @ManyToOne
    private LoiMeasurementUnit loiMeasurementUnit;
}
