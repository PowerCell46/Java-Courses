package bg.softuni.springdataintroexercise.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "towns")
@NoArgsConstructor
@AllArgsConstructor
public class Town extends Base {

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;
}
