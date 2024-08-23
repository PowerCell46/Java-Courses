package bg.softuni.springdataintroexercise.repositories;

import bg.softuni.springdataintroexercise.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
