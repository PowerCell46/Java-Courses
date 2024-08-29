package bg.softuni.xmlprocessingexercise.DTOs.seeding;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class UserSeedDTO {

    @XmlAttribute(name = "first-name")
    private String firstName;

    @XmlAttribute(name = "last-name")
    @NotNull
    @Size(min = 3)
    private String lastName;

    @XmlAttribute
    private int age;
}
