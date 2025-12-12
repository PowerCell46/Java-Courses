package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(
        name = "product_translations",
        indexes = {
                @Index(
                        name = "idx_product_locale_unique",
                        columnList = "product_id, locale_id",
                        unique = true
                )
        }
)
@Getter
@Setter
@SQLDelete(sql = "UPDATE product_translations SET is_deleted = true WHERE id = ?")
@AllArgsConstructor
@NoArgsConstructor
public class ProductTranslation extends CommonEntity {

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "locale_id")
    private Locale locale;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
}
