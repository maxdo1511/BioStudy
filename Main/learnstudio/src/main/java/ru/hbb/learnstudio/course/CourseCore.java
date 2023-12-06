package ru.hbb.learnstudio.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseCore {

    private CourseDataRepository courseDataRepository;

    @Autowired
    public void setCourseDataRepository(CourseDataRepository courseDataRepository) {
        this.courseDataRepository = courseDataRepository;
    }

    public void addCourse(CourseEntity courseEntity) {
        courseDataRepository.save(courseEntity);
    }

    public List<CourseEntity> getAllCourses() {
        return courseDataRepository.findAll();
    }

    public List<CourseEntity> getAllActiveCourses() {
        return courseDataRepository.findAllByActive(true);
    }

}
