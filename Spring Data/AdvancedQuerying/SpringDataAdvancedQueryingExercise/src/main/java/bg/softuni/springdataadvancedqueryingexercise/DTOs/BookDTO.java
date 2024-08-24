package bg.softuni.springdataadvancedqueryingexercise.DTOs;

import bg.softuni.springdataadvancedqueryingexercise.entities.enums.AgeRestriction;
import bg.softuni.springdataadvancedqueryingexercise.entities.enums.EditionType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookDTO {

    private String title;

    private EditionType editionType;

    private AgeRestriction ageRestriction;

    private BigDecimal price;
}
