package ru.hbb.learnstudio.user.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserFieldChangeRequest {

    private String field;
    private String value;

}
