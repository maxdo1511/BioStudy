package ru.hbb.learnstudio.user.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFieldChangeResponse {

    private String last_value;
    private String new_value;

}
