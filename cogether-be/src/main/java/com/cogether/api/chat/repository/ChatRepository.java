package com.cogether.api.chat.repository;

import com.cogether.api.chat.domain.Chat;
import com.cogether.api.chat.domain.ChatRoom;
import com.cogether.api.liveCoop.domain.LiveCoop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
    List<Chat> findAllByChatRoom(ChatRoom chatRoom);

    int countAllByIdAfterAndChatRoom(int id, ChatRoom chatRoom);
}
