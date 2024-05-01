package am.matveev.dance.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bio")
@Getter
@Setter
public class BioEntity{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "profession")
    private String profession;

    @Column(name = "description")
    private String bioDescription;
}
