package bg.softuni.entities.FootballBettingDb;

import bg.softuni.entities.NameBase;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "competitions")
public class Competition extends NameBase {

    @ManyToOne
    @JoinColumn(name = "competition_type", referencedColumnName = "id")
    private CompetitionType competitionType;
}
