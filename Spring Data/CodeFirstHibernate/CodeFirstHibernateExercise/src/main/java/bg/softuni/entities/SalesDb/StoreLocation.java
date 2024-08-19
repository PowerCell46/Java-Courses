package bg.softuni.entities.SalesDb;

import bg.softuni.entities.Base;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "store_locations")
public class StoreLocation extends Base {

    @Column(name = "location_name")
    private String locationName;

    @OneToMany // one store --> many sales
    private Set<Sale> sales;
}
