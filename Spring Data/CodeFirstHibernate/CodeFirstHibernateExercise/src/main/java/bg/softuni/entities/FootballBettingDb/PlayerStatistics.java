package bg.softuni.entities.FootballBettingDb;

import bg.softuni.entities.Base;

import javax.persistence.*;

// TODO: remove ID make it composite key (of game and player)

@Entity
@Table(name = "player_statistics")
public class PlayerStatistics extends Base {

    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    private Player player;

    @Column(name = "scored_goals")
    private int scoredGoals;

    @Column(name = "player_assists")
    private int playerAssists;

    @Column(name = "played_minutes")
    private int playedMinutes;
}
