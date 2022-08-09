package com.cogether.api.hunting.service;

import com.cogether.api.hunting.domain.Hunting;
import com.cogether.api.hunting.domain.HuntingRequest;
import com.cogether.api.hunting.domain.HuntingResponse;
import com.cogether.api.hunting.domain.HuntingScrap;
import com.cogether.api.hunting.exception.HuntingNotFoundException;
import com.cogether.api.hunting.repository.HuntingRepository;
import com.cogether.api.hunting.repository.HuntingScrapRepository;
import com.cogether.api.user.domain.User;
import com.cogether.api.user.exception.UserNotFoundException;
import com.cogether.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HuntingService {

    private final HuntingRepository huntingRepository;
    private final HuntingScrapRepository huntingScrapRepository;
    private final UserRepository userRepository;

    public HuntingResponse.OnlyHuntingId create(HuntingRequest.CreateHunting request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(UserNotFoundException::new);
        Hunting hunting = request.toEntity(user);
        Hunting savedHunting = huntingRepository.save(hunting);
        return HuntingResponse.OnlyHuntingId.build(savedHunting);
    }

    // TODO: 게시물유저 skillList 추가
    public HuntingResponse.GetHunting getHunting(int userId, int huntingId) {
        Hunting hunting = huntingRepository.findById(huntingId).orElseThrow(HuntingNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        int cnt = huntingScrapRepository.countAllByUserAndHunting(user, hunting);

        if (cnt == 0)
            return HuntingResponse.GetHunting.build(hunting, false, 0);

        HuntingScrap huntingScrap = huntingScrapRepository.findByUserAndHunting(user, hunting);
        return HuntingResponse.GetHunting.build(hunting, true, huntingScrap.getId());
    }

    public HuntingResponse.OnlyHuntingId delete(int id) {
        Hunting hunting = huntingRepository.findById(id).orElseThrow(HuntingNotFoundException::new);
        List<HuntingScrap> huntingScrapList = huntingScrapRepository.findAllByHunting(hunting);
        for (int idx = 0; idx < huntingScrapList.size(); idx++)
            huntingScrapRepository.deleteById(huntingScrapList.get(idx).getId());
        huntingRepository.deleteById(id);
        return HuntingResponse.OnlyHuntingId.build(hunting);
    }

    public HuntingResponse.OnlyHuntingScrapId createScrap(HuntingRequest.CreateHuntingScrap request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(UserNotFoundException::new);
        Hunting hunting = huntingRepository.findById(request.getHuntingId()).orElseThrow(HuntingNotFoundException::new);
        HuntingScrap huntingScrap = request.toEntity(user, hunting);
        HuntingScrap savedHuntingScrap = huntingScrapRepository.save(huntingScrap);
        return HuntingResponse.OnlyHuntingScrapId.build(savedHuntingScrap);
    }

    public HuntingResponse.OnlyHuntingScrapId deleteScrap(int id) {
        HuntingScrap huntingScrap = huntingScrapRepository.findById(id).orElseThrow(HuntingNotFoundException::new);
        huntingScrapRepository.deleteById(id);
        return HuntingResponse.OnlyHuntingScrapId.build(huntingScrap);
    }
}
