package bg.softuni.xmlprocessingexercise.DTOs.querying;

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
@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
@Builder
public class CategoryRootQueryDTO {

    @XmlElement(name = "category")
    private List<CategoryQueryDTO> categories;
}
