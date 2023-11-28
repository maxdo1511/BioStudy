package ru.hbb.learnstudio.Courses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.hbb.learnstudio.Courses.Entities.CourseData;
import ru.hbb.learnstudio.Courses.Interfaces.CourseDataRepository;

import java.util.List;

@Component
public class CourseCore {

    private CourseDataRepository courseDataRepository;

    @Autowired
    public void setCourseDataRepository(CourseDataRepository courseDataRepository) {
        this.courseDataRepository = courseDataRepository;
    }

    public void addCourse(CourseData courseData) {
        courseDataRepository.save(courseData);
    }

    public CourseData getCourseDataFromID(String uuid) {
        CourseData courseData = courseDataRepository.findCourseDataByUuid(uuid).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", uuid)
        ));
        return courseData;
    }

    public List<CourseData> getAllCourses() {
        return courseDataRepository.findAll();
    }

    public List<CourseData> getAllActiveCourses() {
        return courseDataRepository.findAllByActive(true);
    }

}
