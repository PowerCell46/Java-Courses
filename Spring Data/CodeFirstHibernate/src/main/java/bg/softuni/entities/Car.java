package bg.softuni.entities;

import bg.softuni.entities.relations.PlateNumber;

import javax.persistence.*;
import javax.swing.*;
import java.math.BigDecimal;


@Entity
@Table(name = "cars")
@DiscriminatorValue("car") // --> For SINGLE_TABLE (name of the value for the defining column for the current Entity)
public class Car extends Vehicle {

    private static final String TYPE = "CAR";

    @Column
    private int seats;

    @OneToOne // (optional=false) // --> A column with the id of the Plate Number
    @JoinColumn(name = "plate_id", referencedColumnName = "id") // --> optional annotation (for changing the column name)
    private PlateNumber plateNumber;

    public Car() {}

    public Car(String model, BigDecimal price, String fuelType, int seats, PlateNumber plateNumber) {
        super(TYPE, model, price, fuelType);
        this.seats = seats;
        this.plateNumber = plateNumber;
    }
}
