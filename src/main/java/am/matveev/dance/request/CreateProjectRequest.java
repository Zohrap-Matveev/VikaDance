package am.matveev.dance.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CreateProjectRequest{

    private String enteredPassword;
    private MultipartFile file;
    private String title;
    private String description;
}
