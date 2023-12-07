package ru.hbb.learnstudio.image;

public enum ImageDir {

    USER_ICO("user-ico/"),
    COURSE_ICO("course-ico/");

    public final String path;

    ImageDir(String path) {
        this.path = path;
    }
}
