package bg.softuni.jsonprocessingexercisepart2.DTOs.queries;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@Builder
public class SupplierQueryDTO {

    @XmlAttribute
    private long id;

    @XmlAttribute
    private String name;

    @XmlAttribute(name = "parts-count")
    private int partsCount;
}
