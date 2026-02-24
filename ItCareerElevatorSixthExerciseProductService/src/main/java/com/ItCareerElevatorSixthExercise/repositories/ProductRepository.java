package com.ItCareerElevatorSixthExercise.repositories;

import com.ItCareerElevatorSixthExercise.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Query("UPDATE Product p set p.inStockQuantity = p.inStockQuantity - :quantity WHERE p.id = :id")
    int decreaseProductInStockQuantity(Integer quantity, Long id);

    @Modifying
    @Query("UPDATE Product p set p.inStockQuantity = p.inStockQuantity + :quantity WHERE p.id = :id")
    void increaseProductInStockQuantity(Integer quantity, Long id);

    @Query("SELECT COALESCE(SUM(p.price), 0) FROM Product p WHERE p.id IN :productIds")
    BigDecimal getProductsPriceSum(@Param("productIds") List<Long> productIds);
}
