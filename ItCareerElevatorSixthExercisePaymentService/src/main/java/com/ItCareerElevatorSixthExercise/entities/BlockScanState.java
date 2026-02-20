package com.ItCareerElevatorSixthExercise.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "block_scan_state")
public class BlockScanState {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private BigInteger lastProcessedBlock;

    public BlockScanState(BigInteger lastProcessedBlock) {
        this.lastProcessedBlock = lastProcessedBlock;
    }
}
