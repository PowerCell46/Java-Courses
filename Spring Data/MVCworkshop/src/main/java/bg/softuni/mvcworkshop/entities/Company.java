package bg.softuni.mvcworkshop.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "companies")
@NoArgsConstructor
public class Company extends Base {

    @Column(nullable = false)
    private String name;

    @OneToMany(targetEntity = Project.class, mappedBy = "company")
    private Set<Project> projects;

    public Company(String name) {
        this.name = name;
        this.projects = new HashSet<>();
    }
}
