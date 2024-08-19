package bg.softuni.entities.FootballBettingDb;

import bg.softuni.entities.NameBase;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "teams")
public class Team extends NameBase {

    @Column
    private String logo;

    @Column(columnDefinition = "CHAR(3)")
    private String initials;

    @ManyToOne
    @JoinColumn(name = "primary_kit_color", referencedColumnName = "id")
    private Color primaryKitColor;

    @ManyToOne
    @JoinColumn(name = "secondary_kit_color", referencedColumnName = "id")
    private Color secondaryKitColor;

    @ManyToOne
    @JoinColumn(name = "town_id", referencedColumnName = "id")
    private Town town;

    @Column
    private BigDecimal budget;
}
