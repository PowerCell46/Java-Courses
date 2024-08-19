package bg.softuni.entities.BillsPaymentSystem;

import bg.softuni.entities.Base;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "users")
public class User extends Base {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String email;

    @Column
    private String password;

    @OneToMany(targetEntity = BillingDetails.class, mappedBy = "owner", fetch = FetchType.LAZY)
    private Set<BillingDetails> billingDetails;
}
