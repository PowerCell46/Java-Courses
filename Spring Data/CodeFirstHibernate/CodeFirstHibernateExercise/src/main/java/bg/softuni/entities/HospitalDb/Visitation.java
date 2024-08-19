package bg.softuni.entities.HospitalDb;

import bg.softuni.entities.Base;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "visitations")
public class Visitation extends Base {

    @Column
    private Date date;

    @Column
    private String comments;

    @ManyToOne(targetEntity = Patient.class)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "diagnose_id", referencedColumnName = "id")
    private Diagnose diagnose;
}
