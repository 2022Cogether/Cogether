package com.cogether.api.user.repository;

import com.cogether.api.user.domain.User;
import com.cogether.api.user.domain.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSkillRepository extends JpaRepository<UserSkill, Integer> {

    List<UserSkill> findBySkillId (int userId);   // 파라미터 : 유저 식별자
}
