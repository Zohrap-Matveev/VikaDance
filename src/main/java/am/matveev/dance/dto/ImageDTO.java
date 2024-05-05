package am.matveev.dance.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ImageDTO{

    private int id;

    @NotEmpty(message = "Name must not be empty")
    private String name;

    @NotEmpty(message = "Image must not be empty")
    private byte[] images;
}
