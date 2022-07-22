package com.cogether.api.til.exception;

import javax.persistence.EntityNotFoundException;

public class TilNotFoundException extends EntityNotFoundException {

    public TilNotFoundException() {
        super("존재하지 않는 게시글입니다.");
    }

}
