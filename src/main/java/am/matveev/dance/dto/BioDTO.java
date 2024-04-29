package am.matveev.dance.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BioDTO{

    private int id;

    @NotEmpty(message = "Image must not be empty")
    private byte[] image;

    @NotEmpty(message = "FullName must not be empty")
    private String fullName;
}
