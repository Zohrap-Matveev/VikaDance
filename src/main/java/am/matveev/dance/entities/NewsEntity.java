package am.matveev.dance.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "news")
@Getter
@Setter
public class NewsEntity{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "date")
    private String date;

    @Column(name = "address")
    private String address;

    @Column(name = "details")
    private String details;
}
