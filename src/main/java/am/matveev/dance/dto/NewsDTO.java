package am.matveev.dance.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsDTO{

    private int id;

    private String title;

    private String date;

    private String address;

    private String details;
}
