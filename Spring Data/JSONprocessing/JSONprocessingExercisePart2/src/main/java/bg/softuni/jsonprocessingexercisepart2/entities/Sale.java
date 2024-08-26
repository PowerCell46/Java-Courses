package bg.softuni.jsonprocessingexercisepart2.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "sales")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sale extends Base {

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @Column
    private int discount;
}
