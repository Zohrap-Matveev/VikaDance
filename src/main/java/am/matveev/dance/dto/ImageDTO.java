package am.matveev.dance.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageDTO{

    private int id;

    private String name;

    private long size;

    private byte[] bytes;
}
