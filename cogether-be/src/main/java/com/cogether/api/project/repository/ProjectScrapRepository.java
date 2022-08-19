package com.cogether.api.project.repository;


import com.cogether.api.project.domain.Project;
import com.cogether.api.project.domain.ProjectScrap;
import com.cogether.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectScrapRepository extends JpaRepository<ProjectScrap, Integer> {
    Integer countAllByProjectAndUser(Project project, User user);

    ProjectScrap findByProject_IdAndUser_Id(int projectId, int userId);

    List<ProjectScrap> findAllByProject_Id(int projectId);
}
