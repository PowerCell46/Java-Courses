package bg.softuni.springdataintro.data.repositories;

import bg.softuni.springdataintro.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
