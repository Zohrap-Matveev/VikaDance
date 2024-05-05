package am.matveev.dance.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "image")
@Getter
@Setter
public class ImageEntity{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 1000000)
    @Lob
    private byte[] images;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private ProjectsEntity projects;

}
