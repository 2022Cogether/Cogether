package com.cogether.api.liveComp.service;

import com.cogether.api.liveComp.domain.LiveComp;
import com.cogether.api.liveComp.domain.LiveCompRequest;
import com.cogether.api.liveComp.domain.LiveCompResponse;
import com.cogether.api.liveComp.repository.LiveCompRepository;
import com.cogether.api.user.domain.User;
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

    public LiveCompResponse.OnlyId update(LiveCompRequest.Update request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(UserNotFoundException::new);
        LiveComp liveComp = findLiveComp(user);
        liveComp.setTotalTime(liveComp.getTotalTime().plusHours(request.getPlusTime().getHour()).plusMinutes(request.getPlusTime().getMinute()).plusSeconds(request.getPlusTime().getSecond()));
        LiveComp savedLiveComp = liveCompRepository.save(liveComp);
        return LiveCompResponse.OnlyId.build(savedLiveComp);
    }

    public LiveCompResponse.GetLiveComp getLiveComp(int userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        // TODO: 시간 수정
        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(0, 0, 0));//어제
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));

        int isExist = liveCompRepository.countAllByCreatedAtBetweenAndUser(startDatetime, endDatetime, user);

        LiveComp liveComp;

        if (isExist == 0) {
            liveComp = LiveComp.toEntity(user, LocalTime.of(0, 0, 0));
            liveCompRepository.save(liveComp);
        }

        liveComp = findLiveComp(user);

        int ranking = 0;
        int cnt = userRepository.countAllByCompIsTrue();
        return LiveCompResponse.GetLiveComp.build(liveComp, ranking, cnt);
    }

    public LiveComp findLiveComp(User user){
        // TODO: 시간 수정
        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(0, 0, 0));//어제
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));

        return liveCompRepository.findByCreatedAtBetweenAndUser(startDatetime, endDatetime, user);
    }

}
