package bg.softuni.entities.SalesDb;

import bg.softuni.entities.Base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;


@Entity
@Table(name = "customers")
public class Customer extends Base {

    @Column
    private String name;

    @Column
    private String email;

    @Column(name = "credit_card_number")
    private String creditCardNumber;

    @OneToMany(mappedBy = "customer") // one customer --> many sales
    private Set<Sale> sales;
}
