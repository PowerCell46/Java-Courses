package bg.softuni.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "bikes")
@DiscriminatorValue("bike") // --> For SINGLE_TABLE (name of the value for the defining column for the current Entity)
public class Bike extends Vehicle {

    private static final String TYPE = "BIKE";

    public Bike() {}

    public Bike(String model, BigDecimal price, String fuelType) {
        super(TYPE, model, price, fuelType);
    }
}

