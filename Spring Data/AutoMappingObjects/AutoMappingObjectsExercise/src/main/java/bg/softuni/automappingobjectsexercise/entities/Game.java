package bg.softuni.automappingobjectsexercise.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "games")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Game extends Base {

    @Column(nullable = false)
    private String title;

    @Column
    private String trailer;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private double size;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Override
    public String toString() {
        return String.format(
            "Title: %s\nPrice: %.2f\nDescription: %s\nRelease Date: %s",
            title,
            price,
            description,
            releaseDate.toString()
        );
    }
}
