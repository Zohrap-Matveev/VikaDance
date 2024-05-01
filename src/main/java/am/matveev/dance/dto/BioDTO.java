package am.matveev.dance.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BioDTO{

    private int id;

    @NotEmpty(message = "FullName must not be empty")
    private String fullName;

    @NotEmpty(message = "Profession must not be empty")
    private String profession;

    @NotEmpty(message = "BioDescription must not be empty")
    private String bioDescription;
}
