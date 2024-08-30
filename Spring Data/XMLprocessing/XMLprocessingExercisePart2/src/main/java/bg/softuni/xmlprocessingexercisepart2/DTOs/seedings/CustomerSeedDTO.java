package bg.softuni.xmlprocessingexercisepart2.DTOs.seedings;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@Builder
public class CustomerSeedDTO {

    @XmlAttribute
    private String name;

    @XmlElement(name = "birth-date")
    private String birthdate;

    @XmlElement(name = "is-young-driver")
    private boolean isYoungDriver;
}
