package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "astronomers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Astronomer extends Base {

    @Column(name = "first_name", nullable = false)
//    @Size(min = 2, max = 30)
    private String firstName;

    @Column(name = "last_name", nullable = false)
//    @Size(min = 2, max = 30)
    private String lastName;

    @Column(nullable = false)
//    @Min(15000)
    private double salary;

    @Column(name = "average_observation_hours", nullable = false)
//    @Min(500)
    private double averageObservationHours;

    @Column
    private LocalDate birthday;

    @ManyToOne
    @JoinColumn(name = "observing_star_id", referencedColumnName = "id")
    private Star observingStar;
}
