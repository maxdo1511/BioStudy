package ru.hbb.learnstudio.Rest.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hbb.learnstudio.Auth.Entities.User;
import ru.hbb.learnstudio.Auth.Interfaces.UserRepository;
import ru.hbb.learnstudio.Utils.ImageUtils;

import javax.activation.FileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/api")
public class ImageController {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/images/{userName}")
    ResponseEntity<?> getUserIcon(@PathVariable String userName) {
        try {
            String imgID = userName +
                    "-icon" +
                    ".png";
            File img = ImageUtils.getImage(imgID);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(Files.readAllBytes(img.toPath()));
        }catch (IOException e) {
            return null;
        }
    }

}
