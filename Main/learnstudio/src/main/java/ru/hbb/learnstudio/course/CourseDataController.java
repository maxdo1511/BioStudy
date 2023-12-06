package ru.hbb.learnstudio.course;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.hbb.learnstudio.user.UserEntity;
import ru.hbb.learnstudio.user.UserRepository;
import ru.hbb.learnstudio.user.enums.UserRole;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/course")
public class CourseDataController {

    private CourseCore courseCore;
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setCourseCore(CourseCore courseCore) {
        this.courseCore = courseCore;
    }

    @GetMapping("/all")
    public String getAllCourses(Principal principal) {
        if (principal == null) {
            return null;
        }
        UserEntity userEntity = userRepository.findUserByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", principal.getName())
        ));
        if (!userEntity.getRole().equalsIgnoreCase(UserRole.ADMIN.toString())) {
            return "No permissions";
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(courseCore.getAllCourses());
            return json;
        }catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/all-active")
    public String getAllActiveCourses() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(courseCore.getAllActiveCourses());
            return json;
        }catch (Exception e) {
            return e.getMessage();
        }
    }

    @PostMapping("/add")
    ResponseEntity<?> addCourse(Principal principal, @RequestBody CourseDataRequest courseDataRequest) {
        if (principal == null) {
            return null;
        }
        UserEntity userEntity = userRepository.findUserByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", principal.getName())
        ));
        if (!userEntity.getRole().equalsIgnoreCase(UserRole.ADMIN.toString())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No permissions");
        }
        try {
            CourseEntity courseEntity = new CourseEntity();
            courseEntity.setName(courseDataRequest.getName());
            courseEntity.setDescription(courseDataRequest.getDescription());
            courseEntity.setCost(courseDataRequest.getCost());
            courseEntity.setDiscount(courseDataRequest.getDiscount());
            courseEntity.setActive(courseDataRequest.isActive());
            courseCore.addCourse(courseEntity);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }

}
