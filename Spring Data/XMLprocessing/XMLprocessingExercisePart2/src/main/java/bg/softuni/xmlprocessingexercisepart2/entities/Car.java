package bg.softuni.xmlprocessingexercisepart2.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Entity
@Table(name = "cars")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Car extends Base {

    @Column
    private String make;

    @Column
    private String model;

    @Column(name = "travelled_distance")
    private long travelledDistance;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "parts_cars",
        joinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "part_id", referencedColumnName = "id")
    )
    private Set<Part> parts;

    @OneToMany(targetEntity = Sale.class, mappedBy = "car", fetch = FetchType.EAGER)
    private Set<Sale> sales;
}
