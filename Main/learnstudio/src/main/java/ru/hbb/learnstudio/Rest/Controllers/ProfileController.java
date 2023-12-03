package ru.hbb.learnstudio.Rest.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.hbb.learnstudio.Auth.Entities.User;
import ru.hbb.learnstudio.Auth.Interfaces.UserRepository;
import ru.hbb.learnstudio.Profile.Data.ProfileDataRequest;
import ru.hbb.learnstudio.Profile.ProfileCore;
import ru.hbb.learnstudio.Utils.ImageUtils;
import ru.hbb.learnstudio.enums.UserRole;

import javax.activation.FileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.UUID;

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

    @PostMapping("/load-icon")
    ResponseEntity<?> load_icon(Principal principal, @RequestBody byte[] request) {
        if (principal == null) {
            return null;
        }
        User user = userRepository.findUserByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", principal.getName())
        ));
        try {
            boolean success = profileCore.changeUserIcon(request, user);
            if (!success) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Can't save image");
            }
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }
}
