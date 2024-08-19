package bg.softuni.entities.SalesDb;

import bg.softuni.entities.Base;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sales")
public class Sale extends Base {

    @ManyToOne // many sales --> one product
    private Product product;

    @ManyToOne // many sales --> one customer
    private Customer customer;

    @ManyToOne // many sales --> one location
    private StoreLocation storeLocation;

    private Date date;
}
