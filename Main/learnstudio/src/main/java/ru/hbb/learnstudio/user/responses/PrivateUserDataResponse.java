package ru.hbb.learnstudio.user.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PrivateUserDataResponse extends UserDataRequest {

    private String username;
    private String email;
    private String phone;
    private String lastAuth;
    private String registerDate;

}
