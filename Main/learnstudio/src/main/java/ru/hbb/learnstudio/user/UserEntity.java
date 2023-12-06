package ru.hbb.learnstudio.user;

import jakarta.persistence.*;
import lombok.Data;
import ru.hbb.learnstudio.annotations.OnlyRoleCanModify;
import ru.hbb.learnstudio.user.enums.UserRole;

@Entity
@Table(name = "users")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OnlyRoleCanModify(role = UserRole.ADMIN)
    private String username;
    @OnlyRoleCanModify(role = UserRole.ADMIN)
    private String email;
    @OnlyRoleCanModify(role = UserRole.ADMIN)
    private String password;
    @OnlyRoleCanModify(role = UserRole.ADMIN)
    private String role;
    private String description;
    private String firstName;
    private String secondName;
    @OnlyRoleCanModify(role = UserRole.ADMIN)
    private String phone;
    @OnlyRoleCanModify(role = UserRole.ADMIN)
    private long registerDate;
    @OnlyRoleCanModify(role = UserRole.ADMIN)
    private long lastAuth;

}
