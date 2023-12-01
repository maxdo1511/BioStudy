package ru.hbb.learnstudio.Profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.hbb.learnstudio.Auth.Entities.User;
import ru.hbb.learnstudio.Auth.Interfaces.UserRepository;
import ru.hbb.learnstudio.Profile.Data.ProfileDataRequest;

import java.security.Principal;

@Component
public class ProfileCore {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ProfileDataRequest getUserData(Principal principal) {
        User user = userRepository.findUserByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", principal.getName())
        ));
        ProfileDataRequest profileDataRequest = new ProfileDataRequest();
        profileDataRequest.setName(user.getUsername());
        profileDataRequest.setDescription(user.getDescription());
        profileDataRequest.setIcon(user.getIcon());
        profileDataRequest.setRole(user.getRole());
        return profileDataRequest;
    }
}
