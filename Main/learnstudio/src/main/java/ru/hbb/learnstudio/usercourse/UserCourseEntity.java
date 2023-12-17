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

    private long userid;
    private String groupname;
    private long start;
    private long enddate;
    private String attends;

}
