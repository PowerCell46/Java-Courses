package bg.softuni.entities.FootballBettingDb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "positions")
public class Position {

    @Id
    @Column(columnDefinition = "CHAR(2)")
    private String id;

    @Column(name = "position_description")
    private String positionDescription;
}
