package bg.softuni.automappingobjectsexercise.repositories;

import bg.softuni.automappingobjectsexercise.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;


@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Modifying
    @Query(value = "UPDATE games SET :property = :val WHERE id = :id", nativeQuery = true)
    int updateStringValueById(String property, String val, Long id);

    @Modifying
    @Query(value = "UPDATE games SET price = :val WHERE id = :id", nativeQuery = true)
    int updatePriceById(BigDecimal val, Long id);

    @Modifying
    @Query(value = "UPDATE games SET size = :val WHERE id = :id", nativeQuery = true)
    int updateSizeById(double val, Long id);

    @Modifying
    @Query(value = "UPDATE games SET release_date = :val WHERE id = :id", nativeQuery = true)
    int updateReleaseDateById(LocalDate val, Long id);

    Optional<Game> findByTitle(String title);
}
