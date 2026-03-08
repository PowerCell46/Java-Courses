package com.ItCareerElevatorSixthExercise.repositories.product;

import com.ItCareerElevatorSixthExercise.entities.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Query("UPDATE Product p set p.inStockQuantity = p.inStockQuantity - :quantity WHERE p.id = :id")
    int decreaseProductInStockQuantity(@Param("quantity") Integer quantity, @Param("id") Long id);

    @Modifying
    @Query("UPDATE Product p set p.inStockQuantity = p.inStockQuantity + :quantity WHERE p.id = :id")
    void increaseProductInStockQuantity(@Param("quantity") Integer quantity, @Param("id") Long id);
}
// TODO: We are bending the versioning rule this way