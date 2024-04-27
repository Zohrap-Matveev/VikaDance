package am.matveev.dance.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteNewsRequest{

    private int newsId;
    private String enteredPassword;
}
