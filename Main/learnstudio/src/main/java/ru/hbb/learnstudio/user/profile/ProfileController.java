package ru.hbb.learnstudio.user.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.hbb.learnstudio.user.UserEntity;
import ru.hbb.learnstudio.user.UserRepository;

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

    @PostMapping("/load-icon")
    ResponseEntity<?> load_icon(Principal principal, @RequestBody byte[] request) {
        if (principal == null) {
            return null;
        }
        UserEntity userEntity = userRepository.findUserByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", principal.getName())
        ));
        try {
            boolean success = profileCore.changeUserIcon(request, userEntity);
            if (!success) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Can't save image");
            }
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }
}
