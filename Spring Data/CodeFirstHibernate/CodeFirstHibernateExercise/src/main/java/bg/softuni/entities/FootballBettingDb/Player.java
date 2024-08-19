package bg.softuni.entities.FootballBettingDb;

import bg.softuni.entities.NameBase;

import javax.persistence.*;


@Entity
@Table(name = "players")
public class Player extends NameBase {

    @Column(name = "squad_number")
    private String squatNumber;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    private Position position;

    @Column(name = "is_injured")
    private boolean isInjured;
}
