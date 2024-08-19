package bg.softuni.entities.GringottsDb;


import bg.softuni.entities.Base;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;


@Entity
@Table(name = "wizard_deposits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WizardDeposit extends Base {

    @Column(name = "first_name", columnDefinition = "VARCHAR(50)")
    private String firstName;

    @Column(name = "last_name", columnDefinition = "VARCHAR(60)", nullable = false)
    private String lastName;

    @Column(columnDefinition = "VARCHAR(1000)")
    private String notes;

    @Column(nullable = false)
    private int age;

    @Column(name = "magic_wand_creator", columnDefinition = "VARCHAR(100)")
    private String magicWandCreator;

    @Column(name = "magic_wand_size")
    private int magicWandSize;

    @Column(name = "deposit_group", columnDefinition = "VARCHAR(20)")
    private String depositGroup;

    @Column(name = "deposit_start_date")
    private LocalDateTime depositStartDate;

    @Column(name = "deposit_amount")
    private double depositAmount;

    @Column(name = "deposit_interest")
    private double depositInterest;

    @Column(name = "deposit_charge")
    private double depositCharge;

    @Column(name = "deposit_expiration_date")
    private LocalDateTime depositExpirationDate;

    @Column(name = "is_deposit_expired")
    private boolean isDepositExpired;
}
