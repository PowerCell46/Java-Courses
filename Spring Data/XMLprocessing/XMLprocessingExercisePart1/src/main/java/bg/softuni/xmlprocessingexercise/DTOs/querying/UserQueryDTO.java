package bg.softuni.xmlprocessingexercise.DTOs.querying;

import jakarta.xml.bind.annotation.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@Builder
public class UserQueryDTO {

    @XmlAttribute(name = "first-name")
    private String firstName;

    @XmlAttribute(name = "last-name")
    private String lastName;

    @XmlElement(name = "sold-products")
    private UserRootSoldProductQueryDTO soldProducts;
}