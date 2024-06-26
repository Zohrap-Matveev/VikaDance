package am.matveev.dance.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class ProjectDTO{

    private int id;

    @NotEmpty(message = "Title must not be empty")
    private String title;

    @NotEmpty(message = "Image must not be empty")
    private byte[] image;

    @NotEmpty(message = "Description must not be empty")
    private String description;

    private List<ImageDTO> imageDTOS;

}
