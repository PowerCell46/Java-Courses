package bg.softuni.entities;

import bg.softuni.Base;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "vehicles")
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // --> Every child has its own table (parent's columns + its own columns) (GeneratedValue strategy = TABLE)
//@Inheritance(strategy = InheritanceType.JOINED) // --> Shared table for the parent Entity, own tables for the children with each of their own columns (GeneratedValue strategy = TABLE/IDENTITY)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // --> Shared table in case of custom columns for a child - added to the main table with null values for other children
@DiscriminatorColumn(name = "vehicle_type") // --> For SINGLE_TABLE (name of the column defining the child type)
public class Vehicle extends Base {

    private String model;

    private BigDecimal price;

    private String fuelType;

    protected Vehicle() {}

    public Vehicle(String type, String model, BigDecimal price, String fuelType) {
        super(type);
        this.model = model;
        this.price = price;
        this.fuelType = fuelType;
    }
}
