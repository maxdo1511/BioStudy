package ru.hbb.learnstudio.usercourse;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usercourse")
@Data
public class UserCourseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private long user;
    private String group;
    private long start;
    private long end_date;
    private String attands;

}
