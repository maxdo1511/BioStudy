package ru.hbb.learnstudio.news;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "News")
@Data
public class NewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String text;
    private String preview;
    private String background;
    private long date;

}
