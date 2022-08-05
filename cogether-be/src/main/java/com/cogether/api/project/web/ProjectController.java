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
    public ResponseEntity<ProjectResponse.OnlyId> create(@RequestBody ProjectRequest.Create_Project project){
        ProjectResponse.OnlyId response = projectService.create(project);
        return ResponseEntity.ok().body(response);
    }


}
