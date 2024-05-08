package am.matveev.dance.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteImageRequest{

    private int projectId;
    private String enteredPassword;
}
