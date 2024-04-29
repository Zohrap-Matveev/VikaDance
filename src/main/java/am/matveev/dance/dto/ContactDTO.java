package am.matveev.dance.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactDTO{

    private int id;

    @NotEmpty(message = "Email must not be empty")
    private String email;

    @NotEmpty(message = "Message must not be empty")
    private String message;
}
