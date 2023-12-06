package ru.hbb.learnstudio.jwt_auth.Data;

import lombok.Data;

@Data
public class SignupRequest {

    private String username;
    private String email;
    private String password;

}
