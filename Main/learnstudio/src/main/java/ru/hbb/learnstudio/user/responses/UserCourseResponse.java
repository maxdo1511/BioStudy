package ru.hbb.learnstudio.user.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCourseResponse {

    private int id;
    private String name;
    private String description;
    private int duration;
    private String start_date;
    private String end_date;

}
