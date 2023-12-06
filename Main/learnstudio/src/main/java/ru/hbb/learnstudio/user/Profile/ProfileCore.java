package ru.hbb.learnstudio.user.Profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.hbb.learnstudio.user.UserEntity;
import ru.hbb.learnstudio.user.UserRepository;
import ru.hbb.learnstudio.image.ImageUtils;
import ru.hbb.learnstudio.user.requests.PrivateSimpleDataResponse;
import ru.hbb.learnstudio.user.requests.PrivateUserDataResponse;
import ru.hbb.learnstudio.user.requests.PublicUserDataResponse;
import ru.hbb.learnstudio.user.requests.UserDataRequest;
import ru.hbb.learnstudio.user.utils.DateUtils;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;

@Component
public class ProfileCore {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public PrivateUserDataResponse getUserData(Principal principal) {
        UserEntity userEntity = userRepository.findUserByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", principal.getName())
        ));
        PrivateUserDataResponse privateUserDataResponse = new PrivateUserDataResponse();
        privateUserDataResponse.setUsername(userEntity.getUsername());
        fillDefaultFields(privateUserDataResponse, userEntity);
        privateUserDataResponse.setRegisterDate(DateUtils.longToDate(userEntity.getRegisterDate()));
        privateUserDataResponse.setLastAuth(DateUtils.longToDate(userEntity.getLastAuth()));
        privateUserDataResponse.setPhone(userEntity.getPhone());
        privateUserDataResponse.setEmail(userEntity.getEmail());
        privateUserDataResponse.setUserCourses(new ArrayList<>());
        return privateUserDataResponse;
    }

    public PublicUserDataResponse getUserData(String name) {
        UserEntity userEntity = userRepository.findUserByUsername(name).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", name)
        ));
        PublicUserDataResponse publicUserDataResponse = new PublicUserDataResponse();
        fillDefaultFields(publicUserDataResponse, userEntity);
        publicUserDataResponse.setRegisterDate(DateUtils.longToDate(userEntity.getRegisterDate()));
        publicUserDataResponse.setLastAuth(DateUtils.longToDate(userEntity.getLastAuth()));
        return publicUserDataResponse;
    }

    public PrivateSimpleDataResponse getSimpleUserData(Principal principal) {
        UserEntity userEntity = userRepository.findUserByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", principal.getName())
        ));
        PrivateSimpleDataResponse privateSimpleDataResponse = new PrivateSimpleDataResponse();
        fillDefaultFields(privateSimpleDataResponse, userEntity);
        privateSimpleDataResponse.setPhone(userEntity.getPhone());
        privateSimpleDataResponse.setUsername(userEntity.getUsername());
        privateSimpleDataResponse.setEmail(userEntity.getEmail());
        return privateSimpleDataResponse;
    }

     public boolean changeUserIcon(byte[] iconBytes, UserEntity userEntity) {
         String iconID = userEntity.getUsername() +
                 "-icon" +
                 ".png";
         try {
             ImageUtils.saveImage(iconBytes, iconID);
         } catch (IOException e) {
             return false;
         }
         return true;
     }

     private void fillDefaultFields(UserDataRequest userDataRequest, UserEntity entity) {
        userDataRequest.setFirstName(entity.getFirstName());
        userDataRequest.setSecondName(entity.getSecondName());
        userDataRequest.setDescription(entity.getDescription());
        userDataRequest.setRole(entity.getRole());
     }
}
