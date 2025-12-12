package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
@Setter
@SQLDelete(sql = "UPDATE products SET is_deleted = true WHERE id = ?")
@AllArgsConstructor
@NoArgsConstructor
public class Product extends CommonEntity {

    @Column(nullable = false)
    private Integer inStockQuantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producer_id", nullable = false)
    private Producer producer;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    // currentDiscountPercentage (when creating add number of days till expiration

    // image (DB file): energo pro OCR (look at the controller)

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private Set<ProductTranslation> translations;

    public Product(BigDecimal price, Integer inStockQuantity, Producer producer) {
        super();

        this.price = price;
        this.inStockQuantity = inStockQuantity;
        this.producer = producer;
    }
}
