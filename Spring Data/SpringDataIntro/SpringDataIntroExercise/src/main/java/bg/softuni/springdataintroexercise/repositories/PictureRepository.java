package bg.softuni.springdataintroexercise.repositories;

import bg.softuni.springdataintroexercise.entities.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Long> {
}
