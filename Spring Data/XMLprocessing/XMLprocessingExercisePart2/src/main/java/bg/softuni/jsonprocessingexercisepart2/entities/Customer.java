package bg.softuni.jsonprocessingexercisepart2.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;


@Entity
@Table(name = "customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends Base {

    @Column
    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "is_young_driver")
    private boolean isYoungDriver;

    @OneToMany(targetEntity = Sale.class, mappedBy = "customer", fetch = FetchType.EAGER)
    private Set<Sale> sales;
}
