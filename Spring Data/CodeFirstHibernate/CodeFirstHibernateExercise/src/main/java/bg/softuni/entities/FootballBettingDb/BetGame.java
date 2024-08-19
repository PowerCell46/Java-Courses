package bg.softuni.entities.FootballBettingDb;

import bg.softuni.entities.Base;

import javax.persistence.*;

// TODO: remove ID make it composite key (of game and bet)

@Entity
@Table(name = "bet_games")
public class BetGame extends Base {

    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "bet_id", referencedColumnName = "id")
    private Bet bet;

    @ManyToOne
    @JoinColumn(name = "result_prediction", referencedColumnName = "id")
    private ResultPrediction resultPrediction;
}
