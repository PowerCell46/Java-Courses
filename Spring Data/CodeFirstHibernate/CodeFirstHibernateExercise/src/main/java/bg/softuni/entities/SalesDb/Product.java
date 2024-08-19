package bg.softuni.entities.SalesDb;

import bg.softuni.entities.Base;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;


@Entity
@Table(name = "products")
public class Product extends Base {

    @Column
    private String name;

    @Column
    private double quantity;

    @Column
    private BigDecimal price;

    @OneToMany(mappedBy = "product") // one product --> many sales
    private Set<Sale> sales;
}
