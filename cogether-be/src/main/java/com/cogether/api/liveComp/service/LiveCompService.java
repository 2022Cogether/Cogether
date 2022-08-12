package com.cogether.api.liveComp.service;

import com.cogether.api.liveComp.domain.LiveCompRequest;
import com.cogether.api.liveComp.domain.LiveCompResponse;
import com.cogether.api.liveComp.exception.RankingNotFoundException;
import com.cogether.api.liveComp.repository.LiveCompRepository;
import com.cogether.api.liveComp.domain.LiveComp;
import com.cogether.api.rank.domain.Ranking;
import com.cogether.api.rank.respository.RankingRepository;
import com.cogether.api.user.domain.User;
import com.cogether.api.user.exception.UserNotFoundException;
import com.cogether.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LiveCompService {

    private final LiveCompRepository liveCompRepository;

    private final UserRepository userRepository;
    private final RankingRepository rankingRepository;

    // TODO: 시간 수정
    private  final LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));//오늘
    private  final LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));

    public LiveCompResponse.OnlyId update(LiveCompRequest.Update request) {
        LiveComp liveComp = findLiveComp(request.getUserId());
        liveComp.setTotalTime(liveComp.getTotalTime() + 1);
        LiveComp savedLiveComp = liveCompRepository.save(liveComp);
        //TODO : findByUserId로 수정 필요할 것 같아요~ :) 우리 기존 유저들 랭킹 테이블에 추가해줘야겠다~
        Ranking ranking = rankingRepository.findByUser_Id(request.getUserId());
        ranking.setWeek(ranking.getWeek() + 1);
        ranking.setMonth(ranking.getMonth() + 1);
        ranking.setTotal(ranking.getTotal() + 1);
        rankingRepository.save(ranking);
        return LiveCompResponse.OnlyId.build(savedLiveComp);
    }

    public LiveCompResponse.GetLiveComp getLiveComp(int userId) {
        LiveComp liveComp = findLiveComp(userId);
        // TODO : 랭킹 구현
        List<LiveComp> rankList= liveCompRepository.findAllByCreatedAtBetweenOrderByTotalTimeDesc(startDatetime, endDatetime);
        int ranking = 1;
        for (int i = 0; i < rankList.size(); i++){
            if(rankList.get(i).getId() == liveComp.getId()){
                break;
            }
            if(rankList.get(i).getTotalTime() > liveComp.getTotalTime()){
                ranking += 1;
            }
        }
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
