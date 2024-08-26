package bg.softuni.jsonprocessingexercisepart2.DTOs.seeds;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class SupplierSeedDTO implements Serializable {

    @Expose
    private String name;

    @Expose
    private boolean isImporter;
}
