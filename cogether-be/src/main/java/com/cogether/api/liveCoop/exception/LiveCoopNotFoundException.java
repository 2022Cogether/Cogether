package com.cogether.api.liveCoop.exception;

import javax.persistence.EntityNotFoundException;

public class LiveCoopNotFoundException extends EntityNotFoundException {

    public LiveCoopNotFoundException() {
        super("존재하지 않는 협력모드 방입니다.");
    }

}
