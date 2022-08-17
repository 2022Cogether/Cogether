package com.cogether.api.project.web;

import com.cogether.api.project.domain.ProjectRequest;
import com.cogether.api.project.domain.ProjectResponse;
import com.cogether.api.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping(path = "/project")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<ProjectResponse.OnlyId> create(@RequestBody ProjectRequest.Create_Project project) {
        ProjectResponse.OnlyId response = projectService.create(project);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(path = "/project/{projectId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ProjectResponse.OnlyId> delete(@PathVariable int projectId) {
        ProjectResponse.OnlyId response = projectService.delete(projectId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/project/{projectId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ProjectResponse.ProjectAll> getProjectDetail(@PathVariable int projectId, @RequestHeader("ACCESS_TOKEN") String token) {
        ProjectResponse.ProjectAll response = projectService.getProjectDetail(projectId, token);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/project/list")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ProjectResponse.ProjectList> getProjectList(@RequestHeader("ACCESS_TOKEN") String token) {
        ProjectResponse.ProjectList response = projectService.getProjectList(token);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(path = "/project/scrap")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<ProjectResponse.OnlyProjectScrapId> createScrap(@RequestBody ProjectRequest.Create_ProjectScrap projectScrap) {
        ProjectResponse.OnlyProjectScrapId response = projectService.createScrap(projectScrap);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(path = "/project/scrap/{projectScrapId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ProjectResponse.OnlyProjectScrapId> deleteScrap(@PathVariable int projectScrapId) {
        ProjectResponse.OnlyProjectScrapId response = projectService.deleteScrap(projectScrapId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/project/list/my")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ProjectResponse.ProjectList> getMyProjectList(@RequestHeader("ACCESS_TOKEN") String token) {
        ProjectResponse.ProjectList response = projectService.getMyProjectList(token);
        return ResponseEntity.ok().body(response);
    }

}
