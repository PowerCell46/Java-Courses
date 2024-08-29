package bg.softuni.xmlprocessingexercise.repositories;

import bg.softuni.xmlprocessingexercise.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Set;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Set<Product> findAllByBuyerIsNullAndPriceBetween(BigDecimal lowerBound, BigDecimal upperBound);
}
