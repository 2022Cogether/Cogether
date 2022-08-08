package com.cogether.api.study.repository;

import com.cogether.api.study.domain.Study;
import com.cogether.api.study.domain.StudyScrap;
import com.cogether.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyScrapRepository extends JpaRepository<StudyScrap, Integer> {

    Integer countAllByStudyAndUser (Study study, User user);
}
