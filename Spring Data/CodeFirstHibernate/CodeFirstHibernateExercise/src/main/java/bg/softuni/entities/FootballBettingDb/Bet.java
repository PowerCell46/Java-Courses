package bg.softuni.entities.FootballBettingDb;

import bg.softuni.entities.Base;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "bets")
public class Bet extends Base {

    @Column(name = "bet_money")
    private BigDecimal betMoney;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private FootballBettingUser user;
}
