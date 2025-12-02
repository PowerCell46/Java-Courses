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
    private String iban;

    @Column
    private LocalDate taxEventDate;

    @Column
    private LocalDate periodFrom;

    @Column
    private LocalDate periodTo;

    @Column(precision = 15, scale = 3)
    private BigDecimal quantity;

    @Column(precision = 15, scale = 2)
    private BigDecimal singlePrice;

    @Column(precision = 15, scale = 2)
    private BigDecimal totalSumWithoutVAT;

    @Column(precision = 15, scale = 2)
    private BigDecimal VAT;

    @ManyToOne
    private LoiDocumentType loiDocumentType;

    @ManyToOne
    private LoiMeasurementUnit loiMeasurementUnit;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private ElectricityInvoice electricityInvoice;

        public Builder() {
            this.electricityInvoice = new ElectricityInvoice();
        }

        public Builder accessPoint(String accessPoint) {
            this.electricityInvoice.accessPoint = accessPoint;
            return this;
        }

        public Builder invoiceNumber(String invoiceNumber) {
            this.electricityInvoice.invoiceNumber = invoiceNumber;
            return this;
        }

        public Builder iban(String iban) {
            this.electricityInvoice.iban = iban;
            return this;
        }

        public Builder taxEventDate(LocalDate taxEventDate) {
            this.electricityInvoice.taxEventDate = taxEventDate;
            return this;
        }

        public Builder periodFrom(LocalDate periodFrom) {
            this.electricityInvoice.periodFrom = periodFrom;
            return this;
        }

        public Builder periodTo(LocalDate periodTo) {
            this.electricityInvoice.periodTo = periodTo;
            return this;
        }

        public Builder quantity(BigDecimal quantity) {
            this.electricityInvoice.quantity = quantity;
            return this;
        }

        public Builder singlePrice(BigDecimal singlePrice) {
            this.electricityInvoice.singlePrice = singlePrice;
            return this;
        }

        public Builder totalSumWithoutVAT(BigDecimal totalSumWithoutVAT) {
            this.electricityInvoice.totalSumWithoutVAT = totalSumWithoutVAT;
            return this;
        }

        public Builder VAT(BigDecimal VAT) {
            this.electricityInvoice.VAT = VAT;
            return this;
        }

        public Builder loiDocumentType(LoiDocumentType loiDocumentType) {
            this.electricityInvoice.loiDocumentType = loiDocumentType;
            return this;
        }

        public Builder loiMeasurementUnit(LoiMeasurementUnit loiMeasurementUnit) {
            this.electricityInvoice.loiMeasurementUnit = loiMeasurementUnit;
            return this;
        }

        public ElectricityInvoice build() {
            if (electricityInvoice.accessPoint == null || electricityInvoice.accessPoint.isBlank()) {
                throw new IllegalStateException("Access point cannot be null or blank.");
            }
            electricityInvoice.accessPoint = electricityInvoice.accessPoint.strip();
            if (!electricityInvoice.accessPoint.matches("^BG\\d{8}$")) {
                throw new IllegalStateException("Access point must start with 'BG' followed by 8 digits.");
            }

            if (electricityInvoice.iban == null || electricityInvoice.iban.isBlank()) {
                throw new IllegalStateException("IBAN cannot be null or blank.");
            }
            electricityInvoice.iban = electricityInvoice.iban.strip();
            if (!electricityInvoice.iban.matches("^BG[0-9A-Za-z]{20}$")) {
                throw new IllegalStateException("IBAN must start with 'BG' followed by 18 letters and digits.");
            }

            if (electricityInvoice.taxEventDate == null) {
                throw new IllegalStateException("Tax event date is required.");
            }
            if (electricityInvoice.taxEventDate.isAfter(LocalDate.now())) {
                throw new IllegalStateException("Tax event date cannot be in the future.");
            }

            if (electricityInvoice.periodFrom == null) {
                throw new IllegalStateException("Period from is required.");
            }
            if (electricityInvoice.periodFrom.isAfter(LocalDate.now())) {
                throw new IllegalStateException("Period from cannot be in the future.");
            }

            if (electricityInvoice.periodTo == null) {
                throw new IllegalStateException("Period to is required.");
            }
            if (electricityInvoice.periodTo.isAfter(LocalDate.now())) {
                throw new IllegalStateException("Period to cannot be in the future.");
            }

            if (electricityInvoice.periodFrom.isAfter(electricityInvoice.periodTo)) {
                throw new IllegalStateException("Period from cannot be after period to.");
            }

            if (electricityInvoice.quantity == null) {
                throw new IllegalStateException("Quantity is required.");
            }
            if (electricityInvoice.quantity.compareTo(new BigDecimal("0.0001")) < 0) {
                throw new IllegalStateException("Quantity must be greater than 0.");
            }

            if (electricityInvoice.singlePrice == null) {
                throw new IllegalStateException("Single price is required.");
            }
            if (electricityInvoice.singlePrice.compareTo(new BigDecimal("0.0001")) < 0) {
                throw new IllegalStateException("Single price must be greater than 0.");
            }

            if (electricityInvoice.totalSumWithoutVAT == null) {
                throw new IllegalStateException("Total sum without VAT is required.");
            }
            if (electricityInvoice.totalSumWithoutVAT.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalStateException("Total sum without VAT must be >= 0.");
            }

            if (electricityInvoice.VAT == null) {
                throw new IllegalStateException("VAT is required.");
            }
            if (electricityInvoice.VAT.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalStateException("VAT must be >= 0.");
            }

            if (electricityInvoice.loiDocumentType == null) {
                throw new IllegalStateException("Document type is required.");
            }

            if (electricityInvoice.loiMeasurementUnit == null) {
                throw new IllegalStateException("Measurement unit is required.");
            }

            return electricityInvoice;
        }
    }
}
