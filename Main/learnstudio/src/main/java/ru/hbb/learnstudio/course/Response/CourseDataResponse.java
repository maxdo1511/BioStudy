package ru.hbb.learnstudio.course.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDataResponse {

    private int id;
    private String name;
    private String description;
    private float final_cost;
    private int discount;
    private int duration;
    private boolean active;

}
