package com.cogether.api.project.repository;


import com.cogether.api.project.domain.Project;
import com.cogether.api.project.domain.ProjectScrap;
import com.cogether.api.user.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectScrapRepository extends JpaRepository<ProjectScrap, Integer> {
    Integer countAllByProjectAndUser(Project project, User user);
}
