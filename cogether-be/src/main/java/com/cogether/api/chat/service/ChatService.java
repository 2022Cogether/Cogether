package com.cogether.api.chat.service;

import com.cogether.api.chat.domain.*;
import com.cogether.api.chat.exception.ChatNotFoundException;
import com.cogether.api.chat.exception.ChatRoomNotFoundException;
import com.cogether.api.chat.repository.ChatMemRepository;
import com.cogether.api.chat.repository.ChatRepository;
import com.cogether.api.chat.repository.ChatRoomRepository;
import com.cogether.api.config.jwt.TokenUtils;
import com.cogether.api.user.domain.User;
import com.cogether.api.user.exception.UserNotFoundException;
import com.cogether.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMemRepository chatMemRepository;
    private final UserRepository userRepository;

    private final TokenUtils tokenUtils;

    public ChatResponse.GetChat createChat(ChatRequest.CreateChat request) {
        ChatRoom chatRoom = chatRoomRepository.findById(request.getChatRoomId()).orElseThrow(ChatRoomNotFoundException::new);
        User user = userRepository.findById(request.getSendUserId()).orElseThrow(UserNotFoundException::new);
        Chat chat = request.toEntity(chatRoom);
        Chat savedChat = chatRepository.save(chat);
        return ChatResponse.GetChat.build(savedChat, user);
    }

    public ChatResponse.GetChat getChat(int chatId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(ChatNotFoundException::new);
        User user = userRepository.findById(chat.getSendUserId()).orElseThrow(UserNotFoundException::new);

        return ChatResponse.GetChat.build(chat, user);
    }

    public ChatResponse.GetChats getChats(int chatRoomId, String token) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(ChatRoomNotFoundException::new);
        List<Chat> chats = chatRepository.findAllByChatRoom(chatRoom);
        List<ChatResponse.GetChat> getChats = new ArrayList<>();
        int len = chats.size();

        if (len > 0) {
            for (int idx = len - 1; idx >= 0; idx--) {
                Chat chat = chats.get(idx);
                User user = userRepository.findById(chat.getSendUserId()).orElseThrow(UserNotFoundException::new);
                getChats.add(ChatResponse.GetChat.build(chat, user));
            }

            User loginUser = userRepository.findById(tokenUtils.getUserIdFromToken(token)).orElseThrow(UserNotFoundException::new);
            ChatMember chatMember = chatMemRepository.findByChatRoomAndUser(chatRoom, loginUser);
            chatMember.setLastReadChatId(chats.get(len - 1).getId());
            chatMemRepository.save(chatMember);
        }

        return ChatResponse.GetChats.build(getChats);
    }

    public ChatResponse.GetChats getCoopChats(int chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(ChatRoomNotFoundException::new);
        List<Chat> chats = chatRepository.findAllByChatRoom(chatRoom);
        List<ChatResponse.GetChat> getChats = new ArrayList<>();
        int len = chats.size();

        for (int idx = len - 1; idx >= 0; idx--) {
            Chat chat = chats.get(idx);
            User user = userRepository.findById(chat.getSendUserId()).orElseThrow(UserNotFoundException::new);
            getChats.add(ChatResponse.GetChat.build(chat, user));
        }

        return ChatResponse.GetChats.build(getChats);
    }

    public ChatResponse.OnlyMemId updateLastChat(ChatRequest.UpdateLastChat request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(UserNotFoundException::new);
        ChatRoom chatRoom = chatRoomRepository.findById(request.getChatRoomId()).orElseThrow(ChatRoomNotFoundException::new);
        ChatMember chatMember = chatMemRepository.findByChatRoomAndUser(chatRoom, user);
        chatMember.setLastReadChatId(request.getChatId());
        ChatMember savedChatMember = chatMemRepository.save(chatMember);
        return ChatResponse.OnlyMemId.build(savedChatMember);
    }

    public ChatResponse.OnlyMemId deleteChatMember(int roomId, String token) {
        User user = userRepository.findById(tokenUtils.getUserIdFromToken(token)).orElseThrow(UserNotFoundException::new);
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(ChatRoomNotFoundException::new);
        ChatMember chatMember = chatMemRepository.findByChatRoomAndUser(chatRoom, user);
        chatMemRepository.deleteChatMemberByChatRoomAndUser(chatRoom, user);

        return ChatResponse.OnlyMemId.build(chatMember);
    }

    public ChatResponse.OnlyRoomId createChatRoom(ChatRequest.CreateChatRoom request, String token) {
        User loginUser = userRepository.findById(tokenUtils.getUserIdFromToken(token)).orElseThrow(UserNotFoundException::new);
        if (loginUser.getId() == request.getChatMemberUserId()) {
            ChatRoom chatRoom = new ChatRoom();
            chatRoom.setId(0);
            return ChatResponse.OnlyRoomId.build(chatRoom);
        }

        List<ChatMember> myChatMembers = chatMemRepository.findAllByUser(loginUser);
        int len = myChatMembers.size();
        for (int idx = 0; idx < len; idx++) {
            ChatMember counterChatMember = chatMemRepository.findByUserNotAndChatRoom(loginUser, myChatMembers.get(idx).getChatRoom());
            if (counterChatMember.getUser().getId() == request.getChatMemberUserId())
                return ChatResponse.OnlyRoomId.build(counterChatMember.getChatRoom());
        }

        User user = userRepository.findById(request.getChatMemberUserId()).orElseThrow(UserNotFoundException::new);
        ChatRoom chatRoom = ChatRoom.toEntity(false);
        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
        ChatMember chatMember = ChatMember.toEntity(savedChatRoom, user);
        chatMemRepository.save(chatMember);
        chatMember = ChatMember.toEntity(savedChatRoom, loginUser);
        chatMemRepository.save(chatMember);
        return ChatResponse.OnlyRoomId.build(savedChatRoom);
    }


    public ChatResponse.GetChatRooms getChatRooms(String token) {
        User loginUser = userRepository.findById(tokenUtils.getUserIdFromToken(token)).orElseThrow(UserNotFoundException::new);
        List<ChatMember> chatMems = chatMemRepository.findAllByUserOrderByCreatedAtDesc(loginUser);
        List<ChatResponse.GetChatRoom> getChatRooms = new ArrayList<>();
        int len = chatMems.size();
        for (int idx = 0; idx < len; idx++) {
            ChatMember nowChatMember = chatMems.get(idx);
            ChatRoom chatRoom = nowChatMember.getChatRoom();
            if (chatRoom.isType())
                continue;
            ChatMember chatMember = chatMemRepository.findByUserNotAndChatRoom(loginUser, chatRoom);
            List<Chat> chats = chatRepository.findAllByChatRoom(chatRoom);
            int cnt = chatRepository.countAllByIdAfterAndChatRoom(nowChatMember.getLastReadChatId(), chatRoom);

            String lastMsg = "";
            if (chats.size() > 0) {
                lastMsg = chats.get(chats.size() - 1).getMessage();
                if (lastMsg.length() > 9)
                    lastMsg = lastMsg.substring(0, 9);
            }

            getChatRooms.add(ChatResponse.GetChatRoom.build(chatMember, lastMsg, cnt));
        }

        return ChatResponse.GetChatRooms.build(getChatRooms);
    }

    public ChatResponse.OnlyRoomId deleteChatRoom(int chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(ChatRoomNotFoundException::new);
        chatRoomRepository.deleteById(chatRoomId);
        return ChatResponse.OnlyRoomId.build(chatRoom);
    }

    public ChatRoom createLiveChatRoom() {
        ChatRoom chatRoom = ChatRoom.toEntity(true);
        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
        return savedChatRoom;
    }
}
