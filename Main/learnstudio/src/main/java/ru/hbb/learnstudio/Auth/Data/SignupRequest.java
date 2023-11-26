package ru.hbb.learnstudio.Auth.Data;

import lombok.Data;

@Data
public class SignupRequest {

    private String username;
    private String email;
    private String password;

}
