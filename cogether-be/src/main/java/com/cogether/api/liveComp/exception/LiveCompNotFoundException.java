package com.cogether.api.liveComp.exception;

import javax.persistence.EntityNotFoundException;

public class LiveCompNotFoundException extends EntityNotFoundException {

    public LiveCompNotFoundException() {
        super("존재하지 않는 경쟁모드 내역입니다.");
    }

}
