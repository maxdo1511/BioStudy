package ru.hbb.learnstudio.course;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseDataRepository extends JpaRepository<CourseEntity, Long> {
    List<CourseEntity> findAll();
    List<CourseEntity> findAllByActive(boolean isActive);

}
