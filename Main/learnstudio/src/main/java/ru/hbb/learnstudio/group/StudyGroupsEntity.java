package ru.hbb.learnstudio.group;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "studygroups")
@Data
public class StudyGroupsEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String name;
    private String description;
    private int courseid;

}
