package ru.hbb.learnstudio.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.hbb.learnstudio.course.Services.CourseService;
import ru.hbb.learnstudio.user.profile.ProfileService;
import ru.hbb.learnstudio.user.enums.UserRole;
import ru.hbb.learnstudio.user.requests.SignUpToCourseRequest;
import ru.hbb.learnstudio.user.requests.UserFieldChangeRequest;
import ru.hbb.learnstudio.user.responses.PrivateSimpleDataResponse;
import ru.hbb.learnstudio.user.responses.PrivateUserDataResponse;
import ru.hbb.learnstudio.user.responses.PublicUserDataResponse;
import ru.hbb.learnstudio.user.responses.UserFieldChangeResponse;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserDataController {

    private ProfileService profileService;

    @Autowired
    public void setProfileCore(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/public/{username}")
    String getPublicUserData(@PathVariable String username) {
        PublicUserDataResponse publicUserDataResponse = profileService.getUserData(username);
        String json = getJSON(publicUserDataResponse);
        return json;
    }

    @GetMapping("/private")
    ResponseEntity<?> getPrivateData(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        PrivateUserDataResponse privateUserDataResponse = profileService.getUserData(principal);
        String json = getJSON(privateUserDataResponse);
        return ResponseEntity.ok(json);
    }

    @GetMapping("/private/{username}")
    ResponseEntity<?> getPrivateDataByAdmin(Principal principal, @PathVariable String username) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        if (!profileService.hasPermission(principal, UserRole.ADMIN)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No permissions");
        }
        PrivateUserDataResponse privateUserDataResponse = profileService.getUserData(principal);
        String json = getJSON(privateUserDataResponse);
        return ResponseEntity.ok(json);
    }

    @GetMapping("/private-simple")
    ResponseEntity<?> getPrivateSimpleData(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        PrivateSimpleDataResponse privateSimpleDataResponse = profileService.getSimpleUserData(principal);
        String json = getJSON(privateSimpleDataResponse);
        return ResponseEntity.ok(json);
    }

    @PostMapping("/redact")
    ResponseEntity<?> setUserField(Principal principal, @RequestBody UserFieldChangeRequest userFieldChangeRequest) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        String last_value = "";
        String new_value = userFieldChangeRequest.getValue();
        try {
             last_value = profileService.changeUserFiled(principal, userFieldChangeRequest.getField(), userFieldChangeRequest.getValue());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getJSON(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getJSON(e.getMessage()));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return ResponseEntity.ok(objectMapper.writeValueAsString(new UserFieldChangeResponse(last_value, new_value)));
        }catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/private/courses")
    ResponseEntity<?> getUserCourses(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        try {
            return ResponseEntity.ok(getJSON(profileService.getUserCourses(principal)));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/usercourse/signup")
    ResponseEntity<?> signUpToCourse(Principal principal, @RequestBody SignUpToCourseRequest signUpToCourseRequest) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        try {
            if (profileService.signUpUserToCourse(principal, signUpToCourseRequest)) {
                return ResponseEntity.ok(null);
            }else {
                throw new RuntimeException("Something going wrong!");
            }
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private String getJSON(Object o) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
}
