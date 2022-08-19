package com.cogether.api.study.repository;

import com.cogether.api.study.domain.Study;
import com.cogether.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyRepository extends JpaRepository<Study, Integer> {

    List<Study> findAllByUserOrderByCreatedAtDesc(User user);
}
