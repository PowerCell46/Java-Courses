package bg.softuni.jsonprocessingexercisepart2.DTOs.queries;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
@Builder
public class SupplierRootQueryDTO {

    @XmlElement(name = "supplier")
    private List<SupplierQueryDTO> suppliers;
}
