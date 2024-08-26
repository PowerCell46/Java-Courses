package bg.softuni.jsonprocessingexercise.DTOs;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
public class UserNextQueryDTO implements Serializable {

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private int age;

    @Expose
    private SoldProductsDTO soldProducts;
}
