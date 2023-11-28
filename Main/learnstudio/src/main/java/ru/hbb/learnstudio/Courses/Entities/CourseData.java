package ru.hbb.learnstudio.Courses.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "courses")
@Data
public class CourseData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;
    private String name;
    private String description;
    private String icon;
    private float cost;
    private int discount;
    private String background;
    private boolean active;
}
