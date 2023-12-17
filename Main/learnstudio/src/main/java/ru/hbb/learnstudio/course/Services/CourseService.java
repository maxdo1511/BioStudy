package ru.hbb.learnstudio.course.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hbb.learnstudio.course.CourseDataRepository;
import ru.hbb.learnstudio.course.CourseEntity;
import ru.hbb.learnstudio.group.GroupRepository;
import ru.hbb.learnstudio.group.StudyGroupsEntity;
import ru.hbb.learnstudio.user.UserEntity;
import ru.hbb.learnstudio.user.UserRepository;
import ru.hbb.learnstudio.usercourse.UserCourseEntity;
import ru.hbb.learnstudio.usercourse.UserCourseRepository;

import java.util.Date;
import java.util.List;

@Service
public class CourseService {


    private CourseDataRepository courseDataRepository;
    private UserCourseRepository userCourseRepository;
    private UserRepository userRepository;
    private GroupRepository groupRepository;

    @Autowired
    public void setCourseDataRepository(CourseDataRepository courseDataRepository) {
        this.courseDataRepository = courseDataRepository;
    }

    @Autowired
    public void setUserCourseRepository(UserCourseRepository userCourseRepository) {
        this.userCourseRepository = userCourseRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setGroupRepository(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public boolean signUpToCourse(UserEntity user, long courseID) {
        //логика оплаты
        StudyGroupsEntity studyGroupsEntity = getAccessibleStudyGroup(courseID);
        if (studyGroupsEntity == null) {
            return false;
        }

        return putUserToGroup(user, studyGroupsEntity, courseID);
    }

    private StudyGroupsEntity getAccessibleStudyGroup(long courseID) {
        List<StudyGroupsEntity> studyGroupsEntities = groupRepository.findAll();
        for (StudyGroupsEntity studyGroupsEntity : studyGroupsEntities) {
            if (studyGroupsEntity.getCourseid() != courseID) {
                continue;
            }
            // проверки
            return studyGroupsEntity;
        }
        return null;
    }

    private boolean putUserToGroup(UserEntity userEntity, StudyGroupsEntity studyGroupsEntity, long courseID) {
        try {
            CourseEntity courseEntity = courseDataRepository.findCourseEntityById(courseID).orElseThrow();

            // логика получения даты окончания
            long date = System.currentTimeMillis();
            date += (long) courseEntity.getDuration() * 7 * 24 * 60 * 60 * 1000;

            UserCourseEntity userCourseEntity = new UserCourseEntity();
            userCourseEntity.setAttends("");
            userCourseEntity.setGroupname(studyGroupsEntity.getName());
            userCourseEntity.setEnddate(date);
            userCourseEntity.setStart(System.currentTimeMillis());
            userCourseEntity.setUserid(userEntity.getId());

            userCourseRepository.save(userCourseEntity);
        }catch (Exception e) {
            return false;
        }
        return true;
    }
}
