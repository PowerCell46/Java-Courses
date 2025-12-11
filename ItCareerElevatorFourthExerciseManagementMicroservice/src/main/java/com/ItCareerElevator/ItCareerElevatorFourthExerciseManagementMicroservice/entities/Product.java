package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import java.math.BigDecimal;

@Entity
@Table(
        name = "products",
        indexes = {
                @Index(name = "idx_en_name_unique", columnList = "en_name", unique = true)
        }
)
@Getter
@Setter
@SQLDelete(sql = "UPDATE products SET is_deleted = true WHERE id = ?")
//@Where(clause = "is_deleted = false")
@AllArgsConstructor
@NoArgsConstructor
public class Product extends CommonEntity {

    @Column(nullable = false, length = 255)
    private String bgName;

    @Column(nullable = false, length = 255)
    private String enName;

    @Column(nullable = false)
    private Integer inStockQuantity;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String bgDescription;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String enDescription;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producer_id", nullable = false)
    private Producer producer;

    // At most 15 significant digits in total (before + after the decimal point)
    // Exactly up to 2 digits to the right of the decimal point
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    // image (DB file)
}
