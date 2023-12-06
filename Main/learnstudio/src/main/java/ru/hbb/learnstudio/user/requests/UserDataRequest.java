package ru.hbb.learnstudio.user.requests;

import lombok.Data;

@Data
public class UserDataRequest {

    private String firstName;
    private String secondName;
    private String description;
    private String role;

}
