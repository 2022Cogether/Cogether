package com.cogether.api.liveCoop.exception;

import javax.persistence.EntityNotFoundException;

public class LiveCoopMemberNotFoundException extends EntityNotFoundException {

    public LiveCoopMemberNotFoundException() {
        super("존재하지 않는 협력모드 유저입니다.");
    }

}
