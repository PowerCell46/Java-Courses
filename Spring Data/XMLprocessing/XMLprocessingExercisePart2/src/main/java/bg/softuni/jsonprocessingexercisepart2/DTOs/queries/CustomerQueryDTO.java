package bg.softuni.jsonprocessingexercisepart2.DTOs.queries;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@Builder
public class CustomerQueryDTO {

    @XmlElement
    private long id;

    @XmlElement
    private String name;

    @XmlElement(name = "birth-date")
    private LocalDate birthdate;

    @XmlElement(name = "is-young-driver")
    private boolean isYoungDriver;
}
