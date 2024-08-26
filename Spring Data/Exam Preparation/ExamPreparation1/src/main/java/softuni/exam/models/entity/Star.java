package softuni.exam.models.entity;

import softuni.exam.models.entity.enums.StarType;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Set;


@Entity
@Table(name = "stars")
public class Star extends Base {

    @Column(unique = true, nullable = false)
    @Size(min = 2, max = 30)
    private String name;

    @Column(name = "light_years", nullable = false)
    @Min(0)
    private double lightYears;

    @Column(nullable = false)
    @Size(min = 6)
    private String description;

    @Column(name = "star_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private StarType starType;

    @ManyToOne
    @JoinColumn(name = "constellation_id", referencedColumnName = "id", nullable = false)
    private Constellation constellation;

    @OneToMany(targetEntity = Astronomer.class, mappedBy = "observingStar", fetch = FetchType.EAGER)
    private Set<Astronomer> observers;
}
