package bg.softuni.entities;

import bg.softuni.entities.relations.Driver;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "trucks")
@DiscriminatorValue("truck") // --> For SINGLE_TABLE (name of the value for the defining column for the current Entity)
public class Truck extends Vehicle {

    private static final String TYPE = "TRUCK";

    @Column(name = "load_capacity")
    private double loadCapacity;

    @ManyToMany
    @JoinTable(name = "trucks_drivers",
            joinColumns = @JoinColumn(name = "truck_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "driver_id", referencedColumnName = "id"))
    private List<Driver> drivers;

    public Truck() {
        this.drivers = new ArrayList<>();
    }

    public Truck(String model, BigDecimal price, String fuelType, double loadCapacity, List<Driver> drivers) {
        super(TYPE, model, price, fuelType);
        this.loadCapacity = loadCapacity;
        this.drivers = drivers;
    }
}
