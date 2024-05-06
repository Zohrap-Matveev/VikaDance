package am.matveev.dance.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsDTO{

    private int id;

    @NotEmpty(message = "Title must not be empty")
    private String title;

    @NotEmpty(message = "Date must not be empty")
    private String date;

    @NotEmpty(message = "Address must not be empty")
    private String address;

    @NotEmpty(message = "Details must not be empty")
    private String details;

}
