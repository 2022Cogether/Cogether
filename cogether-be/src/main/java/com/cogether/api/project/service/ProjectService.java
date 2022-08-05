package com.cogether.api.project.service;

import com.cogether.api.project.domain.Project;
import com.cogether.api.project.domain.ProjectRequest;
import com.cogether.api.project.domain.ProjectResponse;
import com.cogether.api.project.domain.ProjectSkill;
import com.cogether.api.project.exception.ProjectNotFoundException;
import com.cogether.api.project.repository.ProjectRepository;
import com.cogether.api.project.repository.ProjectScrapRepository;
import com.cogether.api.project.repository.ProjectSkillRepository;
import com.cogether.api.user.dto.User;
import com.cogether.api.user.exception.UserNotFoundException;
import com.cogether.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectSkillRepository projectSkillRepository;
    private final ProjectScrapRepository projectScrapRepository;
    private final UserRepository userRepository;

    public ProjectResponse.OnlyId create(ProjectRequest.Create_Project create_project){
        User user = userRepository.findById(create_project.getUserId()).orElseThrow(UserNotFoundException::new);
        System.out.println(user.getNickname());
        Project project = create_project.toEntity(user);
        System.out.println(project.getContent());
        Project savedProject = projectRepository.save(project);
        List<String> list = create_project.getSkillList();
        System.out.println(list.get(0));
        for (int i = 0; i < list.size(); i++){
            String skill = list.get(i);
            ProjectSkill projectSkill = ProjectSkill.toEntity(savedProject, skill);
            projectSkillRepository.save(projectSkill);
        }
        return ProjectResponse.OnlyId.build(savedProject);
    }

    public ProjectResponse.OnlyId delete(int projectId){
        Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
        List<ProjectSkill> list = projectSkillRepository.findAllByProject_Id(projectId);
        for (int i = 0 ; i<list.size(); i++){
            ProjectSkill projectSkill = list.get(i);
            projectSkillRepository.deleteById(projectSkill.getId());
        }
        projectRepository.deleteById(projectId);
        return ProjectResponse.OnlyId.build(project);
    }

    public ProjectResponse.ProjectAll getProjectDetail(int projectId, int userId){
        Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<ProjectSkill> list = projectSkillRepository.findAllByProject_Id(projectId);
        int check = projectScrapRepository.countAllByProjectAndUser(project, user);
        boolean isScrap = false;
        if(check == 1){
            isScrap = true;
        }
        return ProjectResponse.ProjectAll.build(project, list, isScrap);
    }
}
