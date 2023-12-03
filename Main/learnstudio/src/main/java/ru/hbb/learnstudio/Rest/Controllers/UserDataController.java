package ru.hbb.learnstudio.Rest.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hbb.learnstudio.Profile.Data.ProfileDataRequest;
import ru.hbb.learnstudio.Profile.ProfileCore;

@RestController
@RequestMapping("/api")
public class UserDataController {

    private ProfileCore profileCore;

    @Autowired
    public void setProfileCore(ProfileCore profileCore) {
        this.profileCore = profileCore;
    }

    @GetMapping("/userdata/{userName}")
    String getUserData(@PathVariable String userName) {
        ProfileDataRequest profileDataRequest = profileCore.getUserData(userName);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(profileDataRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

}
