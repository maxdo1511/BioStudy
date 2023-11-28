package ru.hbb.learnstudio.Courses.Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hbb.learnstudio.Courses.Entities.CourseData;

import java.util.List;
import java.util.Optional;

public interface CourseDataRepository extends JpaRepository<CourseData, Long> {

    Optional<CourseData> findCourseDataByUuid(String uuid);
    List<CourseData> findAll();
    List<CourseData> findAllByActive(boolean isActive);

}
