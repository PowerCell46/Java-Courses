package bg.softuni.springdataintroexercise.repositories;

import bg.softuni.springdataintroexercise.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
