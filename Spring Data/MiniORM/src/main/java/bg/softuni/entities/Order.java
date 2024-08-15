package bg.softuni.entities;

import bg.softuni.orm.annotations.Column;
import bg.softuni.orm.annotations.Entity;
import bg.softuni.orm.annotations.Id;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "orders")
public class Order {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public Order(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
