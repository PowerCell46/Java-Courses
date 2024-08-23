package bg.softuni.springdataintroexercise.repositories;

import bg.softuni.springdataintroexercise.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
