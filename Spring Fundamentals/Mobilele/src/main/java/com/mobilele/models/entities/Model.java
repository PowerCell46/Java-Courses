package com.mobilele.models.entities;

import com.mobilele.models.enums.Categories;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "models")
public class Model extends BaseTimestamp {

    @Column
    private String name;

    @Column
    @Enumerated(value = EnumType.STRING)
    private Categories category;

    @Column(name = "image_url")
    @Size(min = 8, max = 512)
    private String imageUrl;

    @Column(name = "start_year")
    private int startYear;

    @Column(name = "end_year")
    private int endYear;

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;
}
