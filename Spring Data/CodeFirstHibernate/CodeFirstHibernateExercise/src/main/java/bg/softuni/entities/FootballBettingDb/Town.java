package bg.softuni.entities.FootballBettingDb;

import bg.softuni.entities.Base;
import bg.softuni.entities.NameBase;

import javax.persistence.*;


@Entity
@Table(name = "towns")
public class Town extends NameBase {

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;
}
