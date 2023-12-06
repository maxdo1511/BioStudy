package ru.hbb.learnstudio.jwt_auth.Data;

import lombok.Data;

@Data
public class SigninRequest {

    private String username;
    private String password;

}
