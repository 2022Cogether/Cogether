package com.cogether.api.study.repository;

import com.cogether.api.study.domain.StudySkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudySkillRepository extends JpaRepository<StudySkill, Integer> {

    List<StudySkill> findAllByStudy_Id(int studyId);
}
