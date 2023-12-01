package ru.hbb.learnstudio.Auth.Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.hbb.learnstudio.Auth.Entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);
    Boolean existsUserByUsername(String username);
    Boolean existsUserByEmail(String email);
    @Modifying(clearAutomatically = true)
    @Query("update User user SET user.icon = :icon WHERE user.username = :name")
    void changeUserImage(@Param("icon") String icon, @Param("name") String name);
    Boolean existsUserByIcon(String icon);

}
