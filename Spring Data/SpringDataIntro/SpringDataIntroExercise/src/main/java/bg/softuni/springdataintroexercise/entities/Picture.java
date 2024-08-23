package bg.softuni.springdataintroexercise.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "pictures")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Picture extends Base {

    @Column
    private String title;

    @Column
    private String caption;

    @Column
    private String path;

    @ManyToMany
    @JoinTable(name = "pictures_albums",
        joinColumns = @JoinColumn(name = "picture_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id"))
    private Set<Album> albums;

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private User creator;


}
