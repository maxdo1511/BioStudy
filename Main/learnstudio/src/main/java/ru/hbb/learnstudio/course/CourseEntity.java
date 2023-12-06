package ru.hbb.learnstudio.course;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Course")
@Data
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private float cost;
    private int discount;
    private int duration;
    private boolean active;
}
