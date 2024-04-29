package am.matveev.dance.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDTO{

    private int id;

    @NotEmpty(message = "Title must not be empty")
    private String title;

    @NotEmpty(message = "Image must not be empty")
    private byte[] bytes;

    @NotEmpty(message = "Description must not be empty")
    private String description;

}
