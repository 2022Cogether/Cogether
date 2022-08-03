package com.cogether.api.liveCoop.service;

import com.cogether.api.hunting.domain.Hunting;
import com.cogether.api.hunting.domain.HuntingRequest;
import com.cogether.api.hunting.domain.HuntingResponse;
import com.cogether.api.hunting.exception.HuntingNotFoundException;
import com.cogether.api.hunting.repository.HuntingRepository;
import com.cogether.api.liveCoop.domain.LiveCoop;
import com.cogether.api.liveCoop.domain.LiveCoopRequest;
import com.cogether.api.liveCoop.domain.LiveCoopResponse;
import com.cogether.api.liveCoop.exception.LiveCoopNotFoundException;
import com.cogether.api.liveCoop.repository.LiveCoopRepository;
import com.cogether.api.user.dto.User;
import com.cogether.api.user.exception.UserNotFoundException;
import com.cogether.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class LiveCoopService {

    private final LiveCoopRepository liveCoopRepository;

    private final UserRepository userRepository;

    public LiveCoopResponse.OnlyId create(LiveCoopRequest.Create request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(UserNotFoundException::new);
        LiveCoop liveCoop = request.toEntity(user);
        LiveCoop savedLiveCoop = liveCoopRepository.save(liveCoop);
        return LiveCoopResponse.OnlyId.build(savedLiveCoop);
    }

    public LiveCoopResponse.GetLiveCoop getLiveCoop(int id) {
        LiveCoop liveCoop = liveCoopRepository.findById(id).orElseThrow(LiveCoopNotFoundException::new);
        return LiveCoopResponse.GetLiveCoop.build(liveCoop);
    }

    public LiveCoopResponse.OnlyId update(LiveCoopRequest.Update request) {
        LiveCoop liveCoop = liveCoopRepository.findById(request.getId()).orElseThrow(LiveCoopNotFoundException::new);
        liveCoop.setMemNum(request.getMemNum());
        liveCoop.setDuration(request.getDuration());
        liveCoop.setTitle(request.getTitle());
        liveCoop.setContent(request.getContent());
        liveCoop.setInProgress(request.isInProgress());
        LiveCoop savedLiveCoop = liveCoopRepository.save(liveCoop);
        return LiveCoopResponse.OnlyId.build(savedLiveCoop);
    }

    public LiveCoopResponse.OnlyId delete(int id) {
        LiveCoop liveCoop = liveCoopRepository.findById(id).orElseThrow(LiveCoopNotFoundException::new);
        liveCoopRepository.deleteById(id);
        return LiveCoopResponse.OnlyId.build(liveCoop);
    }

}
