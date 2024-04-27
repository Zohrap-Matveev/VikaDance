package am.matveev.dance.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "bytes")
    private byte[] bytes;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "projects")
    private List<ImageEntity> images = new ArrayList<>();
}
