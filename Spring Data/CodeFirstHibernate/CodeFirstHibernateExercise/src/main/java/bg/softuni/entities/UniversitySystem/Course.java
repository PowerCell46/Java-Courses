package bg.softuni.entities.UniversitySystem;

import bg.softuni.entities.Base;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "courses")
public class Course extends Base {

    @Column
    private String name;

    @Column
    private String description;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column
    private int credits;

    @ManyToMany
    private Set<Student> students;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;
}
