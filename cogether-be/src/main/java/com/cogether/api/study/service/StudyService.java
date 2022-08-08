package com.cogether.api.study.service;

import com.cogether.api.study.domain.Study;
import com.cogether.api.study.domain.StudyRequest;
import com.cogether.api.study.domain.StudyResponse;
import com.cogether.api.study.domain.StudySkill;
import com.cogether.api.study.repository.StudyRepository;
import com.cogether.api.study.repository.StudyScrapRepository;
import com.cogether.api.study.repository.StudySkillRepository;
import com.cogether.api.user.domain.User;
import com.cogether.api.user.exception.UserNotFoundException;
import com.cogether.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;
    private final StudySkillRepository studySkillRepository;
    private final StudyScrapRepository studyScrapRepository;
    private final UserRepository userRepository;

    public StudyResponse.OnlyId create(StudyRequest.Create_Study create_study){
        User user = userRepository.findById(create_study.getUserId()).orElseThrow(UserNotFoundException::new);
        Study study = create_study.toEntity(user);
        Study savedStudy = studyRepository.save(study);
        List<String> list = create_study.getSkillList();
        for (int i = 0; i < list.size(); i++){
            String skill = list.get(i);
            StudySkill studySkill = StudySkill.toEntity(study, skill);
            studySkillRepository.save(studySkill);
        }
        return StudyResponse.OnlyId.build(savedStudy);
    }
}
