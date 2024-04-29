package am.matveev.dance.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageDTO{

    private int id;

    @NotEmpty(message = "Name must not be empty")
    private String name;

    private long size;

    @NotEmpty(message = "Image must not be empty")
    private byte[] bytes;
}
