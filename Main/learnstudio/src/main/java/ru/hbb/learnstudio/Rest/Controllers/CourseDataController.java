package ru.hbb.learnstudio.Rest.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.hbb.learnstudio.Auth.Entities.User;
import ru.hbb.learnstudio.Auth.Interfaces.UserRepository;
import ru.hbb.learnstudio.Courses.CourseCore;
import ru.hbb.learnstudio.Courses.Data.CourseDataRequest;
import ru.hbb.learnstudio.Courses.Entities.CourseData;
import ru.hbb.learnstudio.enums.UserRole;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
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
        User user = userRepository.findUserByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", principal.getName())
        ));
        if (!user.getRole().equalsIgnoreCase(UserRole.ADMIN.toString())) {
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
    ResponseEntity<?> signup(@RequestBody CourseDataRequest courseDataRequest) {
        try {
            String uuid = UUID.randomUUID().toString();
            CourseData courseData = new CourseData();
            courseData.setUuid(uuid);
            courseData.setName(courseDataRequest.getName());
            courseData.setDescription(courseDataRequest.getDescription());
            courseData.setIcon(courseDataRequest.getIcon());
            courseData.setCost(courseDataRequest.getCost());
            courseData.setBackground(courseDataRequest.getBackground());
            courseData.setActive(courseDataRequest.isActive());
            courseCore.addCourse(courseData);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }

}
