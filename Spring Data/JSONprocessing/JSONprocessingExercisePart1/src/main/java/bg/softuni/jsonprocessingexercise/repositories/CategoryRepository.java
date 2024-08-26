package bg.softuni.jsonprocessingexercise.repositories;

import bg.softuni.jsonprocessingexercise.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT " +
        "    categories.name, " +
        "    COUNT(products.id) AS number_of_products, " +
        "    AVG(products.price) AS average_price, " +
        "    SUM(products.price) as total_revenue " +
        "FROM " +
        "    categories " +
        "JOIN products_categories ON " +
        "    categories.id = products_categories.categories_id " +
        "JOIN products ON " +
        "    products_categories.products_id = products.id " +
        "GROUP BY " +
        "    (categories.name) " +
        "ORDER BY " +
        "    COUNT(products.id) DESC;", nativeQuery = true)
    List<Object[]> getCategoriesStatistics();
}
