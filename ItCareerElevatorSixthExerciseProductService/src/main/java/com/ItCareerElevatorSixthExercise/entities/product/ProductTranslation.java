package com.ItCareerElevatorSixthExercise.entities.product;

import com.ItCareerElevatorSixthExercise.entities.CommonEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_translations",
        uniqueConstraints = @UniqueConstraint(
                name="uk_product_locale",
                columnNames = {"product_id", "locale_id"}
        ),
        indexes = @Index(
                name = "idx_product_translations_product",
                columnList = "product_id"
        )
)
public class ProductTranslation extends CommonEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "locale_id", nullable = false)
    private Locale locale;
}
