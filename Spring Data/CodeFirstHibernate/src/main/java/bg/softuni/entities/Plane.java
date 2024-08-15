package bg.softuni.entities;

import bg.softuni.entities.relations.Company;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "planes")
@DiscriminatorValue("plane") // --> For SINGLE_TABLE (name of the value for the defining column for the current Entity)
public class Plane extends Vehicle {

    private static final String TYPE = "PLANE";

    @Column(name = "passenger_capacity")
    private int passengerCapacity;

    @ManyToOne
    @JoinColumn(name = "company_owner", referencedColumnName = "id")
    private Company companyOwner;

    public Plane() {}

    public Plane(String model, BigDecimal price, String fuelType, int passengerCapacity, Company company) {
        super(TYPE, model, price, fuelType);
        this.passengerCapacity = passengerCapacity;
        this.companyOwner = company;
    }
}
