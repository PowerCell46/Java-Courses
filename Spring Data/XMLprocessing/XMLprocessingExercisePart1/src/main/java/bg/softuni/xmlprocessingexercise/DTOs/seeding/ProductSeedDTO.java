package bg.softuni.xmlprocessingexercise.DTOs.seeding;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductSeedDTO {

    @XmlElement
    private String name;

    @XmlElement
    private BigDecimal price;
}
