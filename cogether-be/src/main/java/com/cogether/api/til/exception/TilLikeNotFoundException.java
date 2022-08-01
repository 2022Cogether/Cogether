package com.cogether.api.til.exception;

import javax.persistence.EntityNotFoundException;

public class TilLikeNotFoundException extends EntityNotFoundException {
    public TilLikeNotFoundException() {
        super("존재하지 않는 좋아요입니다.");
    }
}
