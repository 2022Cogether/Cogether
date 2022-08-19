package com.cogether.api.study.exception;

import javax.persistence.EntityNotFoundException;

public class StudyNotFoundException extends EntityNotFoundException {
    public StudyNotFoundException() {
        super("존재하지 않는 스터디 구인입니다.");
    }
}
