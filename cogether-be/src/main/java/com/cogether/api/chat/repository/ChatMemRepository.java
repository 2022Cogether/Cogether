package com.cogether.api.chat.repository;

import com.cogether.api.chat.domain.ChatMember;
import com.cogether.api.chat.domain.ChatRoom;
import com.cogether.api.liveCoop.domain.LiveCoop;
import com.cogether.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMemRepository extends JpaRepository<ChatMember, Integer> {
    void deleteChatMemberByChatRoomAndUser(ChatRoom chatRoom, User user);

    ChatMember findByChatRoomAndUser(ChatRoom chatRoom, User user);

    List<ChatMember> findAllByChatRoom(ChatRoom chatRoom);

    List<ChatMember> findAllByUser(User user);
}
