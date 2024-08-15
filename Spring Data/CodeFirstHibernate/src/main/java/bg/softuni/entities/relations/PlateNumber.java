package bg.softuni.entities.relations;

import bg.softuni.entities.Car;

import javax.persistence.*;
import java.math.BigInteger;


@Entity
@Table(name = "plate_numbers")
public class PlateNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String number;

    @OneToOne(targetEntity = Car.class, mappedBy = "plateNumber")
    private Car car;

    public PlateNumber(String number) {
        this.number = number;
    }

    public PlateNumber() {

    }
}
