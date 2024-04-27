package am.matveev.dance.request;

import am.matveev.dance.dto.NewsDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsRequest{

    private NewsDTO newsDTO;
    private String enteredPassword;
}
