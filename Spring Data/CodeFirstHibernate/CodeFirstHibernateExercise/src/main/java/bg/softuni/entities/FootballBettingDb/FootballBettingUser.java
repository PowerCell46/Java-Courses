package bg.softuni.entities.FootballBettingDb;

import bg.softuni.entities.Base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "football_betting_users")
public class FootballBettingUser extends Base {

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column(unique = true)
    private String email;

    @Column(name = "full_name")
    private String fullName;

    @Column
    private BigDecimal balance;
}
