package com.cogether.api.user.repository;

import com.cogether.api.user.domain.User;
import com.cogether.api.user.domain.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserSkillRepository extends JpaRepository<UserSkill, Integer> {

    List<UserSkill> findAllByUser(User user);

    @Modifying
    @Query("delete from UserSkill u where u.user.id=:userId and u.skillId=:skillId")
    int deleteUserSkill(String skillId,int userId);
}
