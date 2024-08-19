package bg.softuni.entities.FootballBettingDb;

import bg.softuni.entities.Base;
import bg.softuni.entities.FootballBettingDb.Enums.Prediction;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "result_predictions")
public class ResultPrediction extends Base {

    private Prediction prediction;
}

