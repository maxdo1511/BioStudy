package ru.hbb.learnstudio.annotations;

import ru.hbb.learnstudio.user.enums.UserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OnlyRoleCanModify {

    UserRole role() default UserRole.DEFAULT;

}
