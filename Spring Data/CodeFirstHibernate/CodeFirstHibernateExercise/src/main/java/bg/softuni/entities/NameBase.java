package bg.softuni.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class NameBase extends Base {

    @Column
    private String name;
}
