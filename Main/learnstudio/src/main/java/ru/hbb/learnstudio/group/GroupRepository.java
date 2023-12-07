package ru.hbb.learnstudio.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<StudyGroupsEntity, Long> {

    Optional<StudyGroupsEntity> findStudyGroupsEntityByName(String name);

}
