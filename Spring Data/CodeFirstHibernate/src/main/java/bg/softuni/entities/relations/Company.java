package bg.softuni.entities.relations;

import bg.softuni.entities.Plane;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @OneToMany(targetEntity = Plane.class, mappedBy = "companyOwner", fetch = FetchType.LAZY)
    private List<Plane> planes;

    public Company() {
        this.planes = new ArrayList<>();
    }

    public Company(String name) {
        this.name = name;
        this.planes = new ArrayList<>();
    }
}
