package com.cogether.api.liveCoop.service;

import com.cogether.api.chat.domain.ChatRoom;
import com.cogether.api.chat.service.ChatService;
import com.cogether.api.config.jwt.TokenUtils;
import com.cogether.api.liveCoop.domain.LiveCoop;
import com.cogether.api.liveCoop.domain.LiveCoopMember;
import com.cogether.api.liveCoop.domain.LiveCoopRequest;
import com.cogether.api.liveCoop.domain.LiveCoopResponse;
import com.cogether.api.liveCoop.exception.LiveCoopMemberNotFoundException;
import com.cogether.api.liveCoop.exception.LiveCoopNotFoundException;
import com.cogether.api.liveCoop.repository.LiveCoopMemberRepository;
import com.cogether.api.liveCoop.repository.LiveCoopRepository;
import com.cogether.api.user.domain.User;
import com.cogether.api.user.exception.UserNotFoundException;
import com.cogether.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LiveCoopService {

    private final LiveCoopRepository liveCoopRepository;

    private final LiveCoopMemberRepository liveCoopMemberRepository;

    private final UserRepository userRepository;

    private final ChatService chatService;

    private final TokenUtils tokenUtils;

    public LiveCoopResponse.LiveCoopChatRoomLiveCoopMemberId createLiveCoop(LiveCoopRequest.CreateLiveCoop request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(UserNotFoundException::new);
        ChatRoom chatRoom = chatService.createLiveChatRoom();
        LiveCoop liveCoop = request.toEntity(user, chatRoom);
        LiveCoop savedLiveCoop = liveCoopRepository.save(liveCoop);
        LiveCoopMember liveCoopMember = LiveCoopMember.toEntity(user, savedLiveCoop);
        LiveCoopMember savedLiveCoopMember = liveCoopMemberRepository.save(liveCoopMember);
        return LiveCoopResponse.LiveCoopChatRoomLiveCoopMemberId.build(savedLiveCoop.getId(), savedLiveCoop.getChatRoom().getId(), savedLiveCoopMember.getId());
    }

    public LiveCoopResponse.GetLiveCoop getLiveCoop(int id) {
        LiveCoop liveCoop = liveCoopRepository.findById(id).orElseThrow(LiveCoopNotFoundException::new);
        return LiveCoopResponse.GetLiveCoop.build(liveCoop);
    }

    public LiveCoopResponse.GetLiveCoops getLiveCoops(String token) {
        List<LiveCoop> liveCoops = new ArrayList<>();
        User loginUser = userRepository.findById(tokenUtils.getUserIdFromToken(token)).orElseThrow(UserNotFoundException::new);
        int cnt = liveCoopMemberRepository.countAllByUser(loginUser);
        boolean enterCoop = false;
        if (cnt != 0) {
            enterCoop = true;
            LiveCoopMember liveCoopMember = liveCoopMemberRepository.findByUser(loginUser);
            liveCoops.add(liveCoopMember.getLiveCoop());
            liveCoops.addAll(liveCoopRepository.findAllByIdNotOrderByCreatedAtDesc(liveCoopMember.getLiveCoop().getId()));
        } else
            liveCoops = liveCoopRepository.findAllByOrderByCreatedAtDesc();

        return LiveCoopResponse.GetLiveCoops.build(liveCoops, enterCoop);
    }

    public LiveCoopResponse.LiveCoopState startLiveCoop(LiveCoopRequest.StartLiveCoop request) {
        LiveCoop liveCoop = liveCoopRepository.findById(request.getLiveCoopId()).orElseThrow(LiveCoopNotFoundException::new);
        liveCoop.setInProgress(true);
        LiveCoop savedLiveCoop = liveCoopRepository.save(liveCoop);
        List<LiveCoopMember> liveCoopMembers = liveCoopMemberRepository.findAllByLiveCoop(savedLiveCoop);

        for (int idx = 0; idx < liveCoopMembers.size(); idx++) {
            User user = liveCoopMembers.get(idx).getUser();
            user.setExp(user.getExp() + 10);
            userRepository.save(user);
        }

        return LiveCoopResponse.LiveCoopState.build(savedLiveCoop);
    }

    public LiveCoopResponse.GetLiveCoop keepLiveCoop(LiveCoopRequest.StartLiveCoop request) {
        LiveCoop liveCoop = liveCoopRepository.findById(request.getLiveCoopId()).orElseThrow(LiveCoopNotFoundException::new);
        liveCoop.setDuration(liveCoop.getDuration() + 20);
        LiveCoop savedLiveCoop = liveCoopRepository.save(liveCoop);
        return LiveCoopResponse.GetLiveCoop.build(savedLiveCoop);
    }

    public LiveCoopResponse.OnlyLiveCoopId deleteLiveCoop(int id) {
        LiveCoop liveCoop = liveCoopRepository.findById(id).orElseThrow(LiveCoopNotFoundException::new);
        List<LiveCoopMember> liveCoopMembers = liveCoopMemberRepository.findAllByLiveCoop(liveCoop);

        for (int idx = 0; idx < liveCoopMembers.size(); idx++)
            liveCoopMemberRepository.deleteById(liveCoopMembers.get(idx).getId());

        liveCoopRepository.deleteById(id);

        return LiveCoopResponse.OnlyLiveCoopId.build(liveCoop);
    }

    public LiveCoopResponse.LiveCoopChatRoomLiveCoopMemberId createLiveCoopMember(LiveCoopRequest.CreateLiveCoopMember request) {
        LiveCoop liveCoop = liveCoopRepository.findById(request.getLiveCoopId()).orElseThrow(LiveCoopNotFoundException::new);
        if (liveCoop.getNowMemNum() >= liveCoop.getMemNum()) {
            return LiveCoopResponse.LiveCoopChatRoomLiveCoopMemberId.build(0, 0, 0);
        }
        User user = userRepository.findById(request.getUserId()).orElseThrow(UserNotFoundException::new);
        liveCoop.setNowMemNum(liveCoop.getNowMemNum() + 1);
        LiveCoop savedLiveCoop = liveCoopRepository.save(liveCoop);
        LiveCoopMember liveCoopMember = LiveCoopMember.toEntity(user, savedLiveCoop);
        LiveCoopMember savedLiveCoopMember = liveCoopMemberRepository.save(liveCoopMember);
        return LiveCoopResponse.LiveCoopChatRoomLiveCoopMemberId.build(savedLiveCoop.getId(), savedLiveCoop.getChatRoom().getId(), savedLiveCoopMember.getId());
    }

    public LiveCoopResponse.GetLiveCoopMember getLiveCoopMember(int id) {
        LiveCoopMember liveCoopMember = liveCoopMemberRepository.findById(id).orElseThrow(LiveCoopMemberNotFoundException::new);
        return LiveCoopResponse.GetLiveCoopMember.build(liveCoopMember);
    }

    public LiveCoopResponse.GetLiveCoopMembers getLiveCoopMembers(int liveCoopId, String token) {
        User loginUser = userRepository.findById(tokenUtils.getUserIdFromToken(token)).orElseThrow(UserNotFoundException::new);
        LiveCoop liveCoop = liveCoopRepository.findById(liveCoopId).orElseThrow(LiveCoopNotFoundException::new);
        List<LiveCoopMember> liveCoopMembers = liveCoopMemberRepository.findAllByLiveCoop(liveCoop);
        LiveCoopMember liveCoopMember = liveCoopMemberRepository.findByUser(loginUser);
        return LiveCoopResponse.GetLiveCoopMembers.build(liveCoopMembers, liveCoopMember);
    }

    public LiveCoopResponse.OnlyLiveCoopMemberId updateLiveCoopMember(LiveCoopRequest.UpdateLiveCoopMember request) {
        LiveCoopMember liveCoopMember = liveCoopMemberRepository.findById(request.getLiveCoopMemberId()).orElseThrow(LiveCoopMemberNotFoundException::new);
        liveCoopMember.setCode(request.getCode());
        LiveCoopMember savedLiveCoopMember = liveCoopMemberRepository.save(liveCoopMember);
        return LiveCoopResponse.OnlyLiveCoopMemberId.build(savedLiveCoopMember);
    }

    public LiveCoopResponse.OnlyLiveCoopMemberId deleteLiveCoopMember(int id) {
        LiveCoopMember liveCoopMember = liveCoopMemberRepository.findById(id).orElseThrow(LiveCoopMemberNotFoundException::new);
        LiveCoop liveCoop = liveCoopMember.getLiveCoop();
        liveCoop.setNowMemNum(liveCoop.getNowMemNum() - 1);
        liveCoopRepository.save(liveCoop);
        liveCoopMemberRepository.deleteById(id);
        return LiveCoopResponse.OnlyLiveCoopMemberId.build(liveCoopMember);
    }

    public LiveCoopResponse.SocketLiveCoop socketLiveCoop(LiveCoopRequest.SocketLiveCoop request) {
        return LiveCoopResponse.SocketLiveCoop.build(request.getMode());
    }

}
