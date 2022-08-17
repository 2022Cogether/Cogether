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
    public ResponseEntity<StudyResponse.OnlyId> create(@RequestBody StudyRequest.Create_Study study) {
        StudyResponse.OnlyId response = studyService.create(study);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(path = "/study/{studyId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<StudyResponse.OnlyId> delete(@PathVariable int studyId) {
        StudyResponse.OnlyId response = studyService.delete(studyId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/study/{studyId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<StudyResponse.StudyAll> getStudyDetail(@PathVariable int studyId, @RequestHeader("ACCESS_TOKEN") String token) {
        StudyResponse.StudyAll response = studyService.getStudyDetail(studyId, token);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/study/list")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<StudyResponse.StudyList> getStudyList(@RequestHeader("ACCESS_TOKEN") String token) {
        StudyResponse.StudyList response = studyService.getStudyList(token);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/study/list/my")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<StudyResponse.StudyList> getMyStudyList(@RequestHeader("ACCESS_TOKEN") String token) {
        StudyResponse.StudyList response = studyService.getMyStudyList(token);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(path = "/study/scrap")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<StudyResponse.OnlyStudyScrapId> createStudyScrap(@RequestBody StudyRequest.Create_StudyScrap create_studyScrap) {
        StudyResponse.OnlyStudyScrapId response = studyService.createStudyScrap(create_studyScrap);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(path = "/study/scrap/{studyScrapId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<StudyResponse.OnlyStudyScrapId> deleteStudyScrap(@PathVariable int studyScrapId) {
        StudyResponse.OnlyStudyScrapId response = studyService.deleteStudyScrap(studyScrapId);
        return ResponseEntity.ok().body(response);

    }
}
