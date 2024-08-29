package bg.softuni.xmlprocessingexercise.repositories;

import bg.softuni.xmlprocessingexercise.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Set<User> findAllBySoldProductsIsNotEmptyOrderByFirstNameAscLastNameAsc();

    Set<User> findAllBySoldProductsIsNotEmptyOrderBySoldProductsDescLastNameAsc();
}
