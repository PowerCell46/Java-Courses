package bg.softuni.jsonprocessingexercisepart2.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Entity
@Table(name = "suppliers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Supplier extends Base {

    @Column
    private String name;

    @Column(name = "is_importer")
    private boolean isImporter;

    @OneToMany(mappedBy = "supplier", fetch = FetchType.EAGER)
    private Set<Part> parts;
}
