package ru.hbb.learnstudio.Auth.Data;

import lombok.Data;

@Data
public class SigninRequest {

    private String username;
    private String password;

}
