package ru.hbb.learnstudio.Profile.Data;

import lombok.Data;

@Data
public class ProfileDataRequest {

    private String uuid;
    private String name;
    private String description;
    private String icon;
    private String role;
}
