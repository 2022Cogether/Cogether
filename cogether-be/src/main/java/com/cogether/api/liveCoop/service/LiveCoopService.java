package com.cogether.api.liveCoop.service;

import com.cogether.api.liveCoop.domain.LiveCoop;
import com.cogether.api.liveCoop.domain.LiveCoopMember;
import com.cogether.api.liveCoop.domain.LiveCoopRequest;
import com.cogether.api.liveCoop.domain.LiveCoopResponse;
import com.cogether.api.liveCoop.exception.LiveCoopNotFoundException;
import com.cogether.api.liveCoop.repository.LiveCoopMemberRepository;
import com.cogether.api.liveCoop.repository.LiveCoopRepository;
import com.cogether.api.user.domain.User;
import com.cogether.api.user.exception.UserNotFoundException;
import com.cogether.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LiveCoopService {

    private final LiveCoopRepository liveCoopRepository;

    private final LiveCoopMemberRepository liveCoopMemberRepository;

    private final UserRepository userRepository;

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

    public LiveCoopResponse.GetLiveCoops getLiveCoops(int userId) {
        // TODO : 협력모드 진행중인 유저는 협력방리스트 첫 번째가 해당 방이고 true
        List<LiveCoop> liveCoops = liveCoopRepository.findAll();
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return LiveCoopResponse.GetLiveCoops.build(liveCoops);
//        List<LiveCoop> liveCoops = new ArrayList<>();
//        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
//        int cnt = liveCoopRepository.countAllByUser(user);
//        boolean isEnterCoop = false;
//        if (cnt != 0){
//            isEnterCoop = true;
//            liveCoops.add()
//        }else liveCoops = liveCoopRepository.findAll();
//        //findbyuser;
//        return LiveCoopResponse.GetLiveCoops.build(liveCoops);
    }

    public LiveCoopResponse.OnlyLiveCoopId deleteLiveCoop(int id) {
        LiveCoop liveCoop = liveCoopRepository.findById(id).orElseThrow(LiveCoopNotFoundException::new);
        List<LiveCoopMember> liveCoopMembers = liveCoopMemberRepository.findAllByLiveCoop(liveCoop);

        for (int idx = 0; idx < liveCoopMembers.size(); idx++)
            liveCoopMemberRepository.deleteById(liveCoopMembers.get(idx).getId());

        liveCoopRepository.deleteById(id);

        return LiveCoopResponse.OnlyLiveCoopId.build(liveCoop);
    }

}
