package bg.softuni.entities.FootballBettingDb;

import bg.softuni.entities.Base;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "games")
public class Game extends Base {

    @ManyToOne
    @JoinColumn(name = "home_team", referencedColumnName = "id")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team", referencedColumnName = "id")
    private Team awayTeam;

    @Column(name = "home_team_goals")
    private int homeGoals;

    @Column(name = "away_team_goals")
    private int awayGoals;

    @Column(name = "date_time")
    private LocalDate dateTime;

    @Column(name = "home_team_win_bet_rate")
    private double homeTeamWinBetRate;

    @Column(name = "away_team_win_bet_rate")
    private double awayTeamWinBetRate;

    @Column(name = "draw_game_bet_rate")
    private double drawGameBetRate;

    @ManyToOne
    @JoinColumn(name = "round_id", referencedColumnName = "id")
    private Round round;

    @ManyToOne
    @JoinColumn(name = "competition_id", referencedColumnName = "id")
    private Competition competition;
}
