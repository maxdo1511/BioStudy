package ru.hbb.learnstudio.Rest.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.hbb.learnstudio.Auth.Data.SigninRequest;
import ru.hbb.learnstudio.Auth.Entities.User;
import ru.hbb.learnstudio.Auth.Interfaces.UserRepository;
import ru.hbb.learnstudio.Profile.Data.ProfileDataRequest;
import ru.hbb.learnstudio.Profile.ProfileCore;
import ru.hbb.learnstudio.enums.UserRole;

import java.security.Principal;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private ProfileCore profileCore;
    private UserRepository userRepository;

    @Autowired
    public void setProfileCore(ProfileCore profileCore) {
        this.profileCore = profileCore;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/userdata")
    public String getAllCourses(Principal principal) {
        if (principal == null) {
            return null;
        }
        ProfileDataRequest profileDataRequest = profileCore.getUserData(principal);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(profileDataRequest);
            return json;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PostMapping("/load-icon")
    ResponseEntity<?> load_icon(@RequestBody String request) {
        String data = request;
        System.out.println(data);
        return ResponseEntity.ok("Success");
    }
}
