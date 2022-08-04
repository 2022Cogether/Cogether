package com.cogether.api.chat.exception;

import javax.persistence.EntityNotFoundException;

public class ChatRoomNotFoundException extends EntityNotFoundException {

    public ChatRoomNotFoundException() {
        super("존재하지 않는 채팅방입니다.");
    }

}
