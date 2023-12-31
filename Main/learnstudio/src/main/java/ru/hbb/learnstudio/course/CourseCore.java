package ru.hbb.learnstudio.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.hbb.learnstudio.course.Response.CourseDataResponse;

import java.util.ArrayList;
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

    public List<CourseDataResponse> getAllCourses() {
        List<CourseEntity> list = courseDataRepository.findAll();
        List<CourseDataResponse> courseDataResponses = new ArrayList<>();
        for (CourseEntity e : list) {
            courseDataResponses.add(fillCourseData(e, new CourseDataResponse()));
        }
        return courseDataResponses;
    }

    public List<CourseDataResponse> getAllActiveCourses() {
        List<CourseEntity> list = courseDataRepository.findAllByActive(true);
        List<CourseDataResponse> courseDataResponses = new ArrayList<>();
        for (CourseEntity e : list) {
            courseDataResponses.add(fillCourseData(e, new CourseDataResponse()));
        }
        return courseDataResponses;
    }

    private CourseDataResponse fillCourseData(CourseEntity courseEntity, CourseDataResponse courseDataResponse) {
        double discount = (double) courseEntity.getDiscount() / 100;
        if (discount > 0) {
            courseDataResponse.setFinal_cost((float) Math.abs(courseEntity.getCost() * (1 - discount)));
        }else {
            courseDataResponse.setFinal_cost(courseEntity.getCost());
        }
        courseDataResponse.setId(Math.toIntExact(courseEntity.getId()));
        courseDataResponse.setName(courseEntity.getName());
        courseDataResponse.setDescription(courseEntity.getDescription());
        courseDataResponse.setDuration(courseEntity.getDuration());
        courseDataResponse.setDiscount(courseEntity.getDiscount());
        courseDataResponse.setActive(courseEntity.isActive());
        return courseDataResponse;
    }

}
