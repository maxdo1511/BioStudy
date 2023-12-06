package ru.hbb.learnstudio.user;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    private String role;
    private String description;
    private String firstName;
    private String secondName;
    private String phone;
    private long registerDate;
    private long lastAuth;

}
