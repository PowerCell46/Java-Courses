package bg.softuni.entities.UniversitySystem;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "students")
public class Student extends SchoolAssociate {

    @Column(name = "average_grade")
    private double averageGrade;

    @Column
    private int attendance;

    @ManyToMany
    private Set<Course> courses;
}
