package bg.softuni.entities.HospitalDb;

import bg.softuni.entities.Base;
//import jakarta.validation.constraints.Email;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "patients")
public class Patient extends Base {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String address;

    @Column
//    @Email(message = "Invalid Email address!")
    private String email;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column
    private String picture;

    @Column(name = "has_medical_insurance")
    private boolean hasMedicalInsurance;

    @OneToMany(mappedBy = "patient")
    private Set<Visitation> visitations;
}
