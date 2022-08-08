package com.cogether.api.study.web;

import com.cogether.api.study.domain.StudyRequest;
import com.cogether.api.study.domain.StudyResponse;
import com.cogether.api.study.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class StudyController {
    private final StudyService studyService;

    @PostMapping(path = "/study")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<StudyResponse.OnlyId> create(@RequestBody StudyRequest.Create_Study study){
        StudyResponse.OnlyId response = studyService.create(study);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(path = "/study/{studyId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<StudyResponse.OnlyId> delete(@PathVariable int studyId){
        StudyResponse.OnlyId response = studyService.delete(studyId);
        return ResponseEntity.ok().body(response);
    }
}
