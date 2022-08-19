package com.cogether.api.hunting.exception;

import javax.persistence.EntityNotFoundException;

public class HuntingNotFoundException extends EntityNotFoundException {

    public HuntingNotFoundException() {
        super("존재하지 않는 구팀 글입니다.");
    }

}
