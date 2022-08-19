package com.cogether.api.til.exception;

import javax.persistence.EntityNotFoundException;

public class TilCommentNotFoundException extends EntityNotFoundException {
    public TilCommentNotFoundException() {
        super("존재하지 않는 댓글입니다.");
    }
}
