package am.matveev.dance.request;

import am.matveev.dance.dto.BioDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BioRequest{

    private String enteredPassword;
    private BioDTO bioDTO;
}
