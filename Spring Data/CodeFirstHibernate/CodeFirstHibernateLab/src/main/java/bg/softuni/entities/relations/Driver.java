package bg.softuni.entities.relations;


import bg.softuni.entities.Truck;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "full_name")
    private String fullName;

    @ManyToMany(mappedBy = "drivers")
    List<Truck> trucks;

    public Driver() {}

    public Driver(String fullName) {
        this.fullName = fullName;
    }
}
