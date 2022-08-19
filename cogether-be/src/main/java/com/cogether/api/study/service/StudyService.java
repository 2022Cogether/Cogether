package com.cogether.api.study.service;

import com.cogether.api.config.jwt.TokenUtils;
import com.cogether.api.study.domain.*;
import com.cogether.api.study.exception.StudyNotFoundException;
import com.cogether.api.study.repository.StudyRepository;
import com.cogether.api.study.repository.StudyScrapRepository;
import com.cogether.api.study.repository.StudySkillRepository;
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
public class StudyService {

    private final StudyRepository studyRepository;
    private final StudySkillRepository studySkillRepository;
    private final StudyScrapRepository studyScrapRepository;
    private final UserRepository userRepository;
    private final TokenUtils tokenUtils;

    public StudyResponse.OnlyId create(StudyRequest.Create_Study create_study) {
        User user = userRepository.findById(create_study.getUserId()).orElseThrow(UserNotFoundException::new);
        Study study = create_study.toEntity(user);
        Study savedStudy = studyRepository.save(study);
        List<String> list = create_study.getSkillList();
        for (int i = 0; i < list.size(); i++) {
            String skill = list.get(i);
            StudySkill studySkill = StudySkill.toEntity(study, skill);
            studySkillRepository.save(studySkill);
        }
        user.setExp(user.getExp() + 10);
        userRepository.save(user);
        return StudyResponse.OnlyId.build(savedStudy);
    }

    public StudyResponse.OnlyId delete(int studyId) {
        Study study = studyRepository.findById(studyId).orElseThrow(UserNotFoundException::new);
        List<StudySkill> list = studySkillRepository.findAllByStudy_Id(studyId);
        List<StudyScrap> scrapList = studyScrapRepository.findAllByStudy_Id(studyId);
        for (int i = 0; i < list.size(); i++) {
            StudySkill studySkill = list.get(i);
            studySkillRepository.deleteById(studySkill.getId());
        }
        for (int i = 0; i < scrapList.size(); i++) {
            StudyScrap studyScrap = scrapList.get(i);
            studyScrapRepository.deleteById(studyScrap.getId());
        }
        studyRepository.deleteById(studyId);
        return StudyResponse.OnlyId.build(study);
    }

    public StudyResponse.StudyAll getStudyDetail(int studyId, String token) {
        int userId = tokenUtils.getUserIdFromToken(token);
        Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<StudySkill> list = studySkillRepository.findAllByStudy_Id(studyId);
        int check = studyScrapRepository.countAllByStudyAndUser(study, user);
        int scrapId = 0;
        boolean isScrap = false;
        if (check > 0) {
            scrapId = studyScrapRepository.findByStudy_IdAndUser_Id(studyId, userId).getId();
            isScrap = true;
        }
        return StudyResponse.StudyAll.build(study, list, scrapId, isScrap);
    }

    public StudyResponse.StudyList getStudyList(String token) {
        int userId = tokenUtils.getUserIdFromToken(token);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<StudyResponse.StudyAll> studyList = new ArrayList<>();
        List<Study> list = studyRepository.findAll();
        Collections.sort(list, new Comparator<Study>() {
            @Override
            public int compare(Study o1, Study o2) {
                return o2.getCreatedAt().compareTo(o1.getCreatedAt());
            }
        });
        for (int i = 0; i < list.size(); i++) {
            Study study = list.get(i);
            List<StudySkill> studySkillList = studySkillRepository.findAllByStudy_Id(study.getId());
            int check = studyScrapRepository.countAllByStudyAndUser(study, user);
            int scrapId = 0;
            boolean isScrap = false;
            if (check > 0) {
                scrapId = studyScrapRepository.findByStudy_IdAndUser_Id(study.getId(), userId).getId();
                isScrap = true;
            }
            studyList.add(StudyResponse.StudyAll.build(study, studySkillList, scrapId, isScrap));
        }
        return StudyResponse.StudyList.build(studyList);
    }

    public StudyResponse.StudyList getMyStudyList(String token) {
        int userId = tokenUtils.getUserIdFromToken(token);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<StudyResponse.StudyAll> studyList = new ArrayList<>();
        List<Study> list = studyRepository.findAllByUserOrderByCreatedAtDesc(user);
        for (int i = 0; i < list.size(); i++) {
            Study study = list.get(i);
            List<StudySkill> studySkillList = studySkillRepository.findAllByStudy_Id(study.getId());
            int check = studyScrapRepository.countAllByStudyAndUser(study, user);
            int scrapId = 0;
            boolean isScrap = false;
            if (check > 0) {
                scrapId = studyScrapRepository.findByStudy_IdAndUser_Id(study.getId(), userId).getId();
                isScrap = true;
            }
            studyList.add(StudyResponse.StudyAll.build(study, studySkillList, scrapId, isScrap));
        }
        return StudyResponse.StudyList.build(studyList);
    }

    public StudyResponse.OnlyStudyScrapId createStudyScrap(StudyRequest.Create_StudyScrap create_studyScrap) {
        User user = userRepository.findById(create_studyScrap.getUserId()).orElseThrow(UserNotFoundException::new);
        Study study = studyRepository.findById(create_studyScrap.getStudyId()).orElseThrow(StudyNotFoundException::new);
        int check = studyScrapRepository.countAllByStudyAndUser(study, user);
        if(check > 0){
            StudyScrap studyScrap = studyScrapRepository.findByStudy_IdAndUser_Id(study.getId(), user.getId());
            return StudyResponse.OnlyStudyScrapId.build(studyScrap);
        }
        StudyScrap studyScrap = create_studyScrap.toEntity(study, user);
        StudyScrap savedStudyScrap = studyScrapRepository.save(studyScrap);
        return StudyResponse.OnlyStudyScrapId.build(savedStudyScrap);
    }

    public StudyResponse.OnlyStudyScrapId deleteStudyScrap(int studyScrapId) {
        StudyScrap studyScrap = studyScrapRepository.findById(studyScrapId).orElseThrow(StudyNotFoundException::new);
        studyScrapRepository.deleteById(studyScrapId);
        return StudyResponse.OnlyStudyScrapId.build(studyScrap);
    }
}
