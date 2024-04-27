package am.matveev.dance.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteProjectRequest{

    private int projectId;
    private String enteredPassword;
}
