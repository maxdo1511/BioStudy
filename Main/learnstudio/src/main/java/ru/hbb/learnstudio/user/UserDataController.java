package ru.hbb.learnstudio.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hbb.learnstudio.user.Profile.ProfileCore;
import ru.hbb.learnstudio.user.requests.PrivateSimpleDataResponse;
import ru.hbb.learnstudio.user.requests.PrivateUserDataResponse;
import ru.hbb.learnstudio.user.requests.PublicUserDataResponse;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserDataController {

    private ProfileCore profileCore;

    @Autowired
    public void setProfileCore(ProfileCore profileCore) {
        this.profileCore = profileCore;
    }

    @GetMapping("/public/{username}")
    String getPublicUserData(@PathVariable String username) {
        PublicUserDataResponse publicUserDataResponse = profileCore.getUserData(username);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(publicUserDataResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    @GetMapping("/private")
    ResponseEntity<?> getPrivateData(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        PrivateUserDataResponse privateUserDataResponse = profileCore.getUserData(principal);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(privateUserDataResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(json);
    }

    @GetMapping("/private-simple")
    ResponseEntity<?> getPrivateSimpleData(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        PrivateSimpleDataResponse privateSimpleDataResponse = profileCore.getSimpleUserData(principal);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(privateSimpleDataResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(json);
    }

}
