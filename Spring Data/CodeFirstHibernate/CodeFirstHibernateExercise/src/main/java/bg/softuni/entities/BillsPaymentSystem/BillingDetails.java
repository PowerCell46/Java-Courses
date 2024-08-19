package bg.softuni.entities.BillsPaymentSystem;

import bg.softuni.entities.Base;

import javax.persistence.*;


@Entity
@Table(name = "billing_details")
@Inheritance(strategy = InheritanceType.JOINED)
public class BillingDetails extends Base {

    private String number;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;
}
