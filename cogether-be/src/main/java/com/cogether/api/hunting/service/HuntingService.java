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

    // TODO: 게시물유저 skill들, 로그인유저 스크랩여부 추가
    public HuntingResponse.GetHunting getHunting(int id) {
        Hunting hunting = huntingRepository.findById(id).orElseThrow(HuntingNotFoundException::new);
        return HuntingResponse.GetHunting.build(hunting);
    }

    public HuntingResponse.OnlyHuntingId delete(int id) {
        Hunting hunting = huntingRepository.findById(id).orElseThrow(HuntingNotFoundException::new);
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

}
