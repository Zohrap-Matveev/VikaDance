package am.matveev.dance.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "projects")
@Getter
@Setter
public class ProjectsEntity{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title",unique = true)
    private String title;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "projects",fetch = FetchType.LAZY)
    private List<ImageEntity> images;
}
