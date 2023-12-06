package ru.hbb.learnstudio.usercourse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hbb.learnstudio.news.NewsEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourseEntity, Long> {

    List<UserCourseEntity> findAllByUser(long user);

}
