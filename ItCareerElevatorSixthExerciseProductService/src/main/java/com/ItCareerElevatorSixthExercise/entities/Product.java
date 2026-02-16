package com.ItCareerElevatorSixthExercise.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import org.hibernate.annotations.Check;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@Check(constraints = "in_stock_quantity >= 0")
public class Product extends CommonEntity {

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer inStockQuantity;

    @Column(nullable = false, length = 500)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producer_id")
    private Manufacturer manufacturer;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ProductTranslation> translations;

    @Version
    private Long version;

    public Product(BigDecimal price, Integer inStockQuantity, String imageUrl, Manufacturer manufacturer) {
        super();

        this.price = price;
        this.inStockQuantity = inStockQuantity;
        this.imageUrl = imageUrl;
        this.manufacturer = manufacturer;
        this.translations = new HashSet<>();
    }
}
