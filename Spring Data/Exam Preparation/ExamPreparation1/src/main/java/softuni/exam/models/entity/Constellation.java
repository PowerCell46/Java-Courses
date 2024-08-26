package softuni.exam.models.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;


@Entity
@Table(name = "constellations")
public class Constellation extends Base {

    @Column(unique = true, nullable = false)
    @Size(min = 3, max = 20)
    private String name;

    @Column(nullable = false)
    @Size(min = 5)
    private String description;

    @OneToMany(targetEntity = Star.class, mappedBy = "constellation", fetch = FetchType.EAGER)
    private Set<Star> stars;
}
