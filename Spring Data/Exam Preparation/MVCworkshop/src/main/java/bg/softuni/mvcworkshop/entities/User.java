package bg.softuni.mvcworkshop.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "users")
public class User extends Base {

    private String username;

    private String email;

    private String password;
}
