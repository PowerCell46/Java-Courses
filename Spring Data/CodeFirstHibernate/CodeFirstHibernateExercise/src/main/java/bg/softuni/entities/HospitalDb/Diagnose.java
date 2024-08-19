package bg.softuni.entities.HospitalDb;

import bg.softuni.entities.Base;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "diagnoses")
public class Diagnose extends Base {

    @Column
    private String name;

    @Column
    private String comments;


    @OneToMany(targetEntity = Visitation.class, mappedBy = "diagnose", fetch = FetchType.LAZY)
    private Set<Visitation> visitations;

    @OneToMany(targetEntity = Medicament.class, mappedBy = "diagnose", fetch = FetchType.LAZY)
    private Set<Medicament> medicaments;
}
