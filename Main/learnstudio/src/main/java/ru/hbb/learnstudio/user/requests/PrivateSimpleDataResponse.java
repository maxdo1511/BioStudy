package ru.hbb.learnstudio.user.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PrivateSimpleDataResponse extends UserDataRequest {

    private String username;
    private String email;
    private String phone;

}
