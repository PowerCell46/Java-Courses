package bg.softuni.jsonprocessingexercisepart2.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;


@MappedSuperclass
@Getter
public class Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
