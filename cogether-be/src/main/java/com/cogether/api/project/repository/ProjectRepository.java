package com.cogether.api.project.repository;

import com.cogether.api.project.domain.Project;
import com.cogether.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findAllByUserOrderByCreatedAtDesc(User user);
}
