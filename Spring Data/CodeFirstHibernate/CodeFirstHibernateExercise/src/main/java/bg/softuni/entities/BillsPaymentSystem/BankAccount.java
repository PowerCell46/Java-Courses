package bg.softuni.entities.BillsPaymentSystem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bank_account")
public class BankAccount extends BillingDetails {

    @Column
    private String name;

    @Column(name = "SWIFT_code")
    private String SWIFTcode;
}
