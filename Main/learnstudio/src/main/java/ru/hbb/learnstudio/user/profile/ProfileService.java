package ru.hbb.learnstudio.user.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.hbb.learnstudio.course.CourseDataRepository;
import ru.hbb.learnstudio.course.CourseEntity;
import ru.hbb.learnstudio.course.Services.CourseService;
import ru.hbb.learnstudio.group.StudyGroupsEntity;
import ru.hbb.learnstudio.group.GroupRepository;
import ru.hbb.learnstudio.image.ImageDir;
import ru.hbb.learnstudio.user.UserEntity;
import ru.hbb.learnstudio.user.UserRepository;
import ru.hbb.learnstudio.image.ImageUtils;
import ru.hbb.learnstudio.annotations.OnlyRoleCanModify;
import ru.hbb.learnstudio.user.enums.UserRole;
import ru.hbb.learnstudio.user.requests.SignUpToCourseRequest;
import ru.hbb.learnstudio.user.responses.*;
import ru.hbb.learnstudio.user.utils.DateUtils;
import ru.hbb.learnstudio.usercourse.UserCourseEntity;
import ru.hbb.learnstudio.usercourse.UserCourseRepository;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService {

    private UserRepository userRepository;
    private CourseDataRepository courseDataRepository;
    private UserCourseRepository userCourseRepository;
    private GroupRepository groupRepository;
    private CourseService courseService;


    @Autowired
    public void setGroupRepository(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }
    @Autowired
    public void setUserCourseRepository(UserCourseRepository userCourseRepository) {
        this.userCourseRepository = userCourseRepository;
    }
    @Autowired
    public void setCourseDataRepository(CourseDataRepository courseDataRepository) {
        this.courseDataRepository = courseDataRepository;
    }
    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean hasPermission(Principal principal, UserRole userRole) {
        UserEntity userEntity = userRepository.findUserEntitiesByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", principal.getName())
        ));
        if (!userEntity.getRole().equalsIgnoreCase(userRole.toString())) {
            return false;
        }
        return true;
    }

    public boolean hasPermission(UserEntity userEntity, UserRole userRole) {
        if (!userEntity.getRole().equalsIgnoreCase(userRole.toString())) {
            return false;
        }
        return true;
    }

    public PrivateUserDataResponse getUserData(Principal principal) {
        UserEntity userEntity = userRepository.findUserEntitiesByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", principal.getName())
        ));
        PrivateUserDataResponse privateUserDataResponse = new PrivateUserDataResponse();
        privateUserDataResponse.setUsername(userEntity.getUsername());
        fillDefaultFields(privateUserDataResponse, userEntity);
        privateUserDataResponse.setRegisterDate(DateUtils.longToDate(userEntity.getRegisterdate()));
        privateUserDataResponse.setLastAuth(DateUtils.longToDate(userEntity.getLastauth()));
        privateUserDataResponse.setPhone(userEntity.getPhone());
        privateUserDataResponse.setEmail(userEntity.getEmail());
        return privateUserDataResponse;
    }

    public PublicUserDataResponse getUserData(String name) {
        UserEntity userEntity = userRepository.findUserEntitiesByUsername(name).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", name)
        ));
        PublicUserDataResponse publicUserDataResponse = new PublicUserDataResponse();
        fillDefaultFields(publicUserDataResponse, userEntity);
        publicUserDataResponse.setRegisterDate(DateUtils.longToDate(userEntity.getRegisterdate()));
        publicUserDataResponse.setLastAuth(DateUtils.longToDate(userEntity.getLastauth()));
        return publicUserDataResponse;
    }

    public PrivateSimpleDataResponse getSimpleUserData(Principal principal) {
        UserEntity userEntity = userRepository.findUserEntitiesByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", principal.getName())
        ));
        PrivateSimpleDataResponse privateSimpleDataResponse = new PrivateSimpleDataResponse();
        fillDefaultFields(privateSimpleDataResponse, userEntity);
        privateSimpleDataResponse.setPhone(userEntity.getPhone());
        privateSimpleDataResponse.setUsername(userEntity.getUsername());
        privateSimpleDataResponse.setEmail(userEntity.getEmail());
        return privateSimpleDataResponse;
    }

    public List<UserCourseResponse> getUserCourses(Principal principal) {
        UserEntity userEntity = userRepository.findUserEntitiesByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", principal.getName())
        ));
        List<UserCourseEntity> userCourseEntities = userCourseRepository.findUserCourseEntitiesByUserid(userEntity.getId());
        List<UserCourseResponse> userCourseResponses = new ArrayList<>();
        for (UserCourseEntity entity : userCourseEntities) {
            StudyGroupsEntity studyGroupsEntity = groupRepository.findStudyGroupsEntityByName(entity.getGroupname()).orElseThrow();
            CourseEntity courseEntity = courseDataRepository.findCourseEntityById(studyGroupsEntity.getCourseid()).orElseThrow();
            UserCourseResponse userCourseResponse = new UserCourseResponse();
            userCourseResponse.setId(Math.toIntExact(courseEntity.getId()));
            userCourseResponse.setName(courseEntity.getName());
            userCourseResponse.setDuration(courseEntity.getDuration());
            userCourseResponse.setDescription(courseEntity.getDescription());
            userCourseResponse.setEnd_date(DateUtils.longToDate(entity.getEnddate()));
            userCourseResponse.setStart_date(DateUtils.longToDate(entity.getStart()));
            userCourseResponses.add(userCourseResponse);
        }
        return userCourseResponses;
    }

    public String changeUserFiled(Principal principal, String field, String value) throws NoSuchFieldException, IllegalAccessException, RuntimeException {
        UserEntity userEntity = userRepository.findUserEntitiesByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", principal.getName())
        ));
        Class<UserEntity> c = UserEntity.class;
        Field o_field = c.getDeclaredField(field);
        o_field.setAccessible(true);
        for (OnlyRoleCanModify onlyRoleCanModify : o_field.getAnnotationsByType(OnlyRoleCanModify.class)) {
            if (!hasPermission(userEntity, onlyRoleCanModify.role())) {
                throw new RuntimeException("No permission");
            }
        }
        Object last_value = o_field.get(userEntity);
        o_field.set(userEntity, value);
        userRepository.save(userEntity);
        if (last_value != null) {
            return last_value.toString();
        }
        return "";
    }

    public boolean changeUserIcon(byte[] iconBytes, UserEntity userEntity) {
        String iconID = userEntity.getUsername() +
             "-icon" +
             ".png";
        try {
            ImageUtils.saveImage(iconBytes, ImageDir.USER_ICO.path + iconID);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean signUpUserToCourse(Principal principal, SignUpToCourseRequest signUpToCourseRequest) {
        UserEntity userEntity = userRepository.findUserEntitiesByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", principal.getName())
        ));
        return courseService.signUpToCourse(userEntity, signUpToCourseRequest.getCourseID());
    }

    private void fillDefaultFields(UserDataRequest userDataRequest, UserEntity entity) {
        userDataRequest.setFirstName(entity.getFirstname());
        userDataRequest.setSecondName(entity.getSecondname());
        userDataRequest.setDescription(entity.getDescription());
        userDataRequest.setRole(entity.getRole());
    }
}
