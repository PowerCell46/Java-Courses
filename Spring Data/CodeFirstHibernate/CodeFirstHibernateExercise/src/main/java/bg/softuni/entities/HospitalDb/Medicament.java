package bg.softuni.entities.HospitalDb;

import bg.softuni.entities.Base;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "medicaments")
public class Medicament extends Base {

    private String name;

    @ManyToOne
    @JoinColumn(name = "diagnose_id", referencedColumnName = "id")
    private Diagnose diagnose;
}
