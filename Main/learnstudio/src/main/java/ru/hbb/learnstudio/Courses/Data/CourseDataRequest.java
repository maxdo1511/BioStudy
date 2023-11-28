package ru.hbb.learnstudio.Courses.Data;

import lombok.Data;

@Data
public class CourseDataRequest {

    private String uuid;
    private String name;
    private String description;
    private String icon;
    private float cost;
    private int discount;
    private String background;
    private boolean active;

}
