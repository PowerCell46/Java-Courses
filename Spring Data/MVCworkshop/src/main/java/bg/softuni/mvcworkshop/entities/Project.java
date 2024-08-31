package bg.softuni.mvcworkshop.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
public class Project extends Base {

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "is_finished")
    private boolean isFinished;

    @Column(nullable = false)
    private BigDecimal payment;

    @Column(name = "start_date")
    private LocalDate startDate;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    private Company company;

    @OneToMany(targetEntity = Employee.class, mappedBy = "project")
    private Set<Employee> employees;

    public Project(String name, String description, boolean isFinished, BigDecimal payment, LocalDate startDate, Company company) {
        this.name = name;
        this.description = description;
        this.isFinished = isFinished;
        this.payment = payment;
        this.startDate = startDate;
        this.company = company;
        this.employees = new HashSet<>();
    }
}
