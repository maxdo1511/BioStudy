package ru.hbb.learnstudio.user.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PublicUserDataResponse extends UserDataRequest {

    private String lastAuth;
    private String registerDate;

}
