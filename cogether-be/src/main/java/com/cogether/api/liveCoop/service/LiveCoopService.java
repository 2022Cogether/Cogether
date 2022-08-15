package com.cogether.api.liveCoop.service;

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
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LiveCoopService {

    private final LiveCoopRepository liveCoopRepository;

    private final LiveCoopMemberRepository liveCoopMemberRepository;

    private final UserRepository userRepository;

    private final TokenUtils tokenUtils;

    public LiveCoopResponse.OnlyLiveCoopId createLiveCoop(LiveCoopRequest.CreateLiveCoop request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(UserNotFoundException::new);
        LiveCoop liveCoop = request.toEntity(user);
        LiveCoop savedLiveCoop = liveCoopRepository.save(liveCoop);
        LiveCoopMember liveCoopMember = LiveCoopMember.toEntity(user, savedLiveCoop);
        liveCoopMemberRepository.save(liveCoopMember);
        return LiveCoopResponse.OnlyLiveCoopId.build(savedLiveCoop);
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
            liveCoops.add(liveCoopRepository.findByUser(loginUser));
            liveCoops.addAll(liveCoopRepository.findAllByIdNotOrderByCreatedAtDesc(liveCoops.get(0).getId()));
        } else liveCoops = liveCoopRepository.findAllByOrderByCreatedAtDesc();

        return LiveCoopResponse.GetLiveCoops.build(liveCoops, enterCoop);
    }

    public LiveCoopResponse.OnlyLiveCoopId startLiveCoop(@RequestBody LiveCoopRequest.StartLiveCoop request) {
        LiveCoop liveCoop = liveCoopRepository.findById(request.getLiveCoopId()).orElseThrow(LiveCoopNotFoundException::new);
        liveCoop.setInProgress(true);
        LiveCoop savedLiveCoop = liveCoopRepository.save(liveCoop);
        return LiveCoopResponse.OnlyLiveCoopId.build(savedLiveCoop);
    }

    public LiveCoopResponse.OnlyLiveCoopId deleteLiveCoop(int id) {
        LiveCoop liveCoop = liveCoopRepository.findById(id).orElseThrow(LiveCoopNotFoundException::new);
        List<LiveCoopMember> liveCoopMembers = liveCoopMemberRepository.findAllByLiveCoop(liveCoop);

        for (int idx = 0; idx < liveCoopMembers.size(); idx++)
            liveCoopMemberRepository.deleteById(liveCoopMembers.get(idx).getId());

        liveCoopRepository.deleteById(id);

        return LiveCoopResponse.OnlyLiveCoopId.build(liveCoop);
    }

    public LiveCoopResponse.OnlyLiveCoopMemberId createLiveCoopMember(LiveCoopRequest.CreateLiveCoopMember request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(UserNotFoundException::new);
        LiveCoop liveCoop = liveCoopRepository.findById(request.getLiveCoopId()).orElseThrow(LiveCoopNotFoundException::new);
        LiveCoopMember liveCoopMember = LiveCoopMember.toEntity(user, liveCoop);
        LiveCoopMember savedLiveCoopMember = liveCoopMemberRepository.save(liveCoopMember);
        return LiveCoopResponse.OnlyLiveCoopMemberId.build(savedLiveCoopMember);
    }

    public LiveCoopResponse.GetLiveCoopMember getLiveCoopMember(int id) {
        LiveCoopMember liveCoopMember = liveCoopMemberRepository.findById(id).orElseThrow(LiveCoopMemberNotFoundException::new);
        return LiveCoopResponse.GetLiveCoopMember.build(liveCoopMember);
    }

    public LiveCoopResponse.GetLiveCoopMembers getLiveCoopMembers(int liveCoopId) {
        LiveCoop liveCoop = liveCoopRepository.findById(liveCoopId).orElseThrow(LiveCoopNotFoundException::new);
        List<LiveCoopMember> liveCoopMembers = liveCoopMemberRepository.findAllByLiveCoop(liveCoop);
        return LiveCoopResponse.GetLiveCoopMembers.build(liveCoopMembers);
    }

    public LiveCoopResponse.OnlyLiveCoopMemberId updateLiveCoopMember(LiveCoopRequest.UpdateLiveCoopMember request) {
        LiveCoopMember liveCoopMember = liveCoopMemberRepository.findById(request.getLiveCoopMemberId()).orElseThrow(LiveCoopMemberNotFoundException::new);
        liveCoopMember.setCode(request.getCode());
        LiveCoopMember savedLiveCoopMember = liveCoopMemberRepository.save(liveCoopMember);
        return LiveCoopResponse.OnlyLiveCoopMemberId.build(savedLiveCoopMember);
    }

    public LiveCoopResponse.OnlyLiveCoopMemberId deleteLiveCoopMember(int id) {
        LiveCoopMember liveCoopMember = liveCoopMemberRepository.findById(id).orElseThrow(LiveCoopMemberNotFoundException::new);
        liveCoopMemberRepository.deleteById(id);
        return LiveCoopResponse.OnlyLiveCoopMemberId.build(liveCoopMember);
    }

}
