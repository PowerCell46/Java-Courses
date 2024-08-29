package bg.softuni.xmlprocessingexercise.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NameBase extends Base {

    @Column(unique = true)
    @Size(min = 3, max = 15)
    private String name;
}
