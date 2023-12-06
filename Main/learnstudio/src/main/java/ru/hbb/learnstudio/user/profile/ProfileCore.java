package ru.hbb.learnstudio.user.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.hbb.learnstudio.course.CourseDataRepository;
import ru.hbb.learnstudio.course.CourseEntity;
import ru.hbb.learnstudio.group.StudyGroupsEntity;
import ru.hbb.learnstudio.group.GroupRepository;
import ru.hbb.learnstudio.user.UserEntity;
import ru.hbb.learnstudio.user.UserRepository;
import ru.hbb.learnstudio.image.ImageUtils;
import ru.hbb.learnstudio.annotations.OnlyRoleCanModify;
import ru.hbb.learnstudio.user.enums.UserRole;
import ru.hbb.learnstudio.user.responses.*;
import ru.hbb.learnstudio.user.utils.DateUtils;
import ru.hbb.learnstudio.usercourse.UserCourseEntity;
import ru.hbb.learnstudio.usercourse.UserCourseRepository;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProfileCore {

    private UserRepository userRepository;
    private CourseDataRepository courseDataRepository;
    private UserCourseRepository userCourseRepository;
    private GroupRepository groupRepository;

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
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean hasPermission(Principal principal, UserRole userRole) {
        UserEntity userEntity = userRepository.findUserByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException(
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
        UserEntity userEntity = userRepository.findUserByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", principal.getName())
        ));
        PrivateUserDataResponse privateUserDataResponse = new PrivateUserDataResponse();
        privateUserDataResponse.setUsername(userEntity.getUsername());
        fillDefaultFields(privateUserDataResponse, userEntity);
        privateUserDataResponse.setRegisterDate(DateUtils.longToDate(userEntity.getRegisterDate()));
        privateUserDataResponse.setLastAuth(DateUtils.longToDate(userEntity.getLastAuth()));
        privateUserDataResponse.setPhone(userEntity.getPhone());
        privateUserDataResponse.setEmail(userEntity.getEmail());
        return privateUserDataResponse;
    }

    public PublicUserDataResponse getUserData(String name) {
        UserEntity userEntity = userRepository.findUserByUsername(name).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", name)
        ));
        PublicUserDataResponse publicUserDataResponse = new PublicUserDataResponse();
        fillDefaultFields(publicUserDataResponse, userEntity);
        publicUserDataResponse.setRegisterDate(DateUtils.longToDate(userEntity.getRegisterDate()));
        publicUserDataResponse.setLastAuth(DateUtils.longToDate(userEntity.getLastAuth()));
        return publicUserDataResponse;
    }

    public PrivateSimpleDataResponse getSimpleUserData(Principal principal) {
        UserEntity userEntity = userRepository.findUserByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException(
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
        UserEntity userEntity = userRepository.findUserByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", principal.getName())
        ));
        List<UserCourseEntity> userCourseEntities = userCourseRepository.findAllByUser(userEntity.getId());
        List<UserCourseResponse> userCourseResponses = new ArrayList<>();
        for (UserCourseEntity entity : userCourseEntities) {
            String group = entity.getGroup();
            StudyGroupsEntity studyGroupsEntity = groupRepository.findAll().get(0);
            CourseEntity courseEntity = courseDataRepository.findCourseEntityById(studyGroupsEntity.getCourse()).orElseThrow();
            UserCourseResponse userCourseResponse = new UserCourseResponse();
            userCourseResponse.setName(courseEntity.getName());
            userCourseResponse.setDuration(courseEntity.getDuration());
            userCourseResponse.setDescription(courseEntity.getDescription());
            userCourseResponse.setEnd_date(DateUtils.longToDate(entity.getEnd_date()));
            userCourseResponse.setStart_date(DateUtils.longToDate(entity.getStart()));
            userCourseResponses.add(userCourseResponse);
        }
        return userCourseResponses;
    }

    public String changeUserFiled(Principal principal, String field, String value) throws NoSuchFieldException, IllegalAccessException, RuntimeException {
        UserEntity userEntity = userRepository.findUserByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException(
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
        String last_value = o_field.get(userEntity).toString();
        o_field.set(userEntity, value);
        userRepository.save(userEntity);
        return last_value;
    }

    public boolean changeUserIcon(byte[] iconBytes, UserEntity userEntity) {
        String iconID = userEntity.getUsername() +
             "-icon" +
             ".png";
        try {
         ImageUtils.saveImage(iconBytes, iconID);
        } catch (IOException e) {
         return false;
        }
        return true;
    }

    private void fillDefaultFields(UserDataRequest userDataRequest, UserEntity entity) {
        userDataRequest.setFirstName(entity.getFirstName());
        userDataRequest.setSecondName(entity.getSecondName());
        userDataRequest.setDescription(entity.getDescription());
        userDataRequest.setRole(entity.getRole());
    }
}
