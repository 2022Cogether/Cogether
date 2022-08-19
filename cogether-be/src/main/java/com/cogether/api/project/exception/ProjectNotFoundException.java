package com.cogether.api.project.exception;

import javax.persistence.EntityNotFoundException;

public class ProjectNotFoundException extends EntityNotFoundException {
    public ProjectNotFoundException() {
        super("존재하지 않는 프로텍트 구인 입니다.");
    }
}
