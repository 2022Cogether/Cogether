package com.cogether.api.chat.repository;

import com.cogether.api.chat.domain.ChatMember;
import com.cogether.api.chat.domain.ChatRoom;
import com.cogether.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMemRepository extends JpaRepository<ChatMember, Integer> {
    void deleteChatMemberByChatRoomAndUser(ChatRoom chatRoom, User user);

    ChatMember findByChatRoomAndUser(ChatRoom chatRoom, User user);

    ChatMember findByUserNotAndChatRoom(User user, ChatRoom chatRoom);

    List<ChatMember> findAllByChatRoom(ChatRoom chatRoom);

    List<ChatMember> findAllByUserOrderByCreatedAtDesc(User user);

    List<ChatMember> findAllByUser(User user);
}
