package com.cogether.api.liveComp.service;

import com.cogether.api.liveComp.domain.LiveCompRequest;
import com.cogether.api.liveComp.domain.LiveCompResponse;
import com.cogether.api.liveComp.repository.LiveCompRepository;
import com.cogether.api.liveComp.domain.LiveComp;
import com.cogether.api.user.dto.User;
import com.cogether.api.user.exception.UserNotFoundException;
import com.cogether.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class LiveCompService {

    private final LiveCompRepository liveCompRepository;

    private final UserRepository userRepository;

    // TODO: 시간 수정
    private  final LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(0, 0, 0));//어제
    private  final LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));

    public LiveCompResponse.OnlyId update(LiveCompRequest.Update request) {
        LiveComp liveComp = findLiveComp(request.getUserId());
        liveComp.setTotalTime(liveComp.getTotalTime() + 1);
        LiveComp savedLiveComp = liveCompRepository.save(liveComp);
        return LiveCompResponse.OnlyId.build(savedLiveComp);
    }

    public LiveCompResponse.GetLiveComp getLiveComp(int userId) {
        LiveComp liveComp = findLiveComp(userId);

        // TODO : 랭킹 구현
        int ranking = 0;
        int cnt = userRepository.countAllByCompIsTrue();
        return LiveCompResponse.GetLiveComp.build(liveComp, ranking, cnt);
    }

    public LiveComp findLiveComp(int userId){

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        int isExist = liveCompRepository.countAllByCreatedAtBetweenAndUser(startDatetime, endDatetime, user);
        LiveComp liveComp;

        if (isExist == 0) {
            liveComp = LiveComp.toEntity(user);
            liveCompRepository.save(liveComp);
        }

        return liveCompRepository.findByCreatedAtBetweenAndUser(startDatetime, endDatetime, user);
    }

}
