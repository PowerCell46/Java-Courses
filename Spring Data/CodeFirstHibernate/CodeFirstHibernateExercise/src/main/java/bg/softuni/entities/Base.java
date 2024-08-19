package bg.softuni.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public class Base {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
}
