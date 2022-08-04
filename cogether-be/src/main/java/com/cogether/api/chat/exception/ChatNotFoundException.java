package com.cogether.api.chat.exception;

import javax.persistence.EntityNotFoundException;

public class ChatNotFoundException extends EntityNotFoundException {

    public ChatNotFoundException() {
        super("존재하지 않는 채팅입니다.");
    }

}
