package com.cogether.api.project.service;

import com.cogether.api.project.domain.*;
import com.cogether.api.project.exception.ProjectNotFoundException;
import com.cogether.api.project.repository.ProjectRepository;
import com.cogether.api.project.repository.ProjectScrapRepository;
import com.cogether.api.project.repository.ProjectSkillRepository;
import com.cogether.api.user.domain.User;
import com.cogether.api.user.exception.UserNotFoundException;
import com.cogether.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        Project project = create_project.toEntity(user);
        Project savedProject = projectRepository.save(project);
        List<String> list = create_project.getSkillList();
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
        int scrapId = 0;
        boolean isScrap = false;
        if(check == 1){
            scrapId = projectScrapRepository.findByProject_IdAndUser_Id(projectId, userId).getId();
            isScrap = true;
        }
        return ProjectResponse.ProjectAll.build(project, list, scrapId,isScrap);
    }

    public ProjectResponse.ProjectList getProjectList(int userId){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<ProjectResponse.ProjectAll> projectList = new ArrayList<>();
        List<Project> list = projectRepository.findAll();
        Collections.sort(list, new Comparator<Project>() {
            @Override
            public int compare(Project o1, Project o2) {
                return o2.getCreatedAt().compareTo(o1.getCreatedAt());
            }
        });
        for (int i = 0; i < list.size(); i++){
            Project project = list.get(i);
            List<ProjectSkill> projectSkillList = projectSkillRepository.findAllByProject_Id(project.getId());
            int check = projectScrapRepository.countAllByProjectAndUser(project, user);
            int scrapId = 0;
            boolean isScrap = false;
            if(check == 1){
                scrapId = projectScrapRepository.findByProject_IdAndUser_Id(project.getId(), userId).getId();
                isScrap = true;
            }
            projectList.add(ProjectResponse.ProjectAll.build(project, projectSkillList, scrapId, isScrap));
        }
        return ProjectResponse.ProjectList.build(projectList);
    }

    public ProjectResponse.OnlyProjectScrapId createScrap(ProjectRequest.Create_ProjectScrap create_projectScrap){
        User user = userRepository.findById(create_projectScrap.getUserId()).orElseThrow(UserNotFoundException::new);
        Project project = projectRepository.findById(create_projectScrap.getProjectId()).orElseThrow(ProjectNotFoundException::new);
        ProjectScrap projectScrap = create_projectScrap.toEntity(project, user);
        ProjectScrap savedProjectScrap = projectScrapRepository.save(projectScrap);
        return ProjectResponse.OnlyProjectScrapId.build(savedProjectScrap);
    }

    public ProjectResponse.OnlyProjectScrapId deleteScrap(int projectScrapId){
        ProjectScrap projectScrap = projectScrapRepository.findById(projectScrapId).orElseThrow(ProjectNotFoundException::new);
        projectScrapRepository.deleteById(projectScrapId);
        return ProjectResponse.OnlyProjectScrapId.build(projectScrap);
    }

    public ProjectResponse.ProjectList getMyProjectList(int userId){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<ProjectResponse.ProjectAll> projectList = new ArrayList<>();
        List<Project> list = projectRepository.findAllByUserOrderByCreatedAtDesc(user);
        for (int i = 0; i < list.size(); i++){
            Project project = list.get(i);
            List<ProjectSkill> projectSkillList = projectSkillRepository.findAllByProject_Id(project.getId());
            int check = projectScrapRepository.countAllByProjectAndUser(project, user);
            int scrapId = 0;
            boolean isScrap = false;
            if(check == 1){
                scrapId = projectScrapRepository.findByProject_IdAndUser_Id(project.getId(), userId).getId();
                isScrap = true;
            }
            projectList.add(ProjectResponse.ProjectAll.build(project, projectSkillList, scrapId, isScrap));
        }
        return ProjectResponse.ProjectList.build(projectList);
    }
}
