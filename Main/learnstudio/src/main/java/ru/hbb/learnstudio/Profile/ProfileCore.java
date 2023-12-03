package ru.hbb.learnstudio.Profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.hbb.learnstudio.Auth.Entities.User;
import ru.hbb.learnstudio.Auth.Interfaces.UserRepository;
import ru.hbb.learnstudio.Profile.Data.ProfileDataRequest;
import ru.hbb.learnstudio.Utils.ImageUtils;

import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

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

    public ProfileDataRequest getUserData(String name) {
        User user = userRepository.findUserByUsername(name).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", name)
        ));
        ProfileDataRequest profileDataRequest = new ProfileDataRequest();
        profileDataRequest.setName(user.getUsername());
        profileDataRequest.setDescription(user.getDescription());
        profileDataRequest.setIcon(user.getIcon());
        profileDataRequest.setRole(user.getRole());
        return profileDataRequest;
    }

     public boolean changeUserIcon(byte[] iconBytes, User user) {
        String iconID = user.getUsername() +
             "-icon" +
             ".png";
        boolean hasIcon = userRepository.existsUserByIcon(iconID);
        if (hasIcon) {
            try {
                ImageUtils.removeImage(iconID);
            } catch (IOException e) {
                return false;
            }
        }
        try {
            ImageUtils.saveImage(iconBytes, iconID);
        } catch (IOException e) {
            return false;
        }
        try {
            user.setIcon(iconID);
            userRepository.save(user);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
