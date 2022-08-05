package com.cogether.api.project.repository;

import com.cogether.api.project.domain.Project;
import com.cogether.api.project.domain.ProjectSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectSkillRepository extends JpaRepository<ProjectSkill, Integer> {
    List<ProjectSkill> findAllByProject_Id(int projectId);
}
