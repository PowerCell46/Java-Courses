package com.mobilele.models.entities;

import com.mobilele.models.enums.Engines;
import com.mobilele.models.enums.Transmissions;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "offers")
public class Offer extends BaseTimestamp {

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    @Enumerated(value = EnumType.STRING)
    private Engines engine;

    @Column
    private String imageUrl;

    @Column
    private int mileage;

    @Column
    private BigDecimal price;

    @Column
    @Enumerated(value = EnumType.STRING)
    private Transmissions transmission;

    @Column
    private int year;

    @ManyToOne
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    private Model model;

    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    private User seller;
}
