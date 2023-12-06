package ru.hbb.learnstudio.course;

import lombok.Data;

@Data
public class CourseDataRequest {

    private String name;
    private String description;
    private float cost;
    private int discount;
    private int duration;
    private boolean active;

}
