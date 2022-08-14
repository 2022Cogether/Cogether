package com.cogether.api.rank.service;

import com.cogether.api.follow.domain.Follow;
import com.cogether.api.follow.repository.FollowRepository;
import com.cogether.api.rank.domain.Ranking;
import com.cogether.api.rank.domain.RankingResponse;
import com.cogether.api.rank.respository.RankingRepository;
import com.cogether.api.user.domain.User;
import com.cogether.api.user.exception.UserNotFoundException;
import com.cogether.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankService {

    private final RankingRepository rankingRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public RankingResponse.TilRankList getTilRank(int userId, int page){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Ranking> list = rankingRepository.findAllByOrderByTilCntDesc();
        int startIndex = 20*(page-1);
        int endIndex = 20*(page);
        if(endIndex > list.size()) {
            endIndex = list.size();
        }
        List<RankingResponse.TilRank> tilRankList = new ArrayList<>();
        for (int i = startIndex; i < endIndex; i++){
            int rank = i+1;
            Ranking ranking = list.get(i);
            tilRankList.add(RankingResponse.TilRank.build(rank, ranking.getUser(), ranking.getTilCnt()));
        }
        Ranking myRanking = rankingRepository.findByUser_Id(userId);
        int myRank = 1;
        for (int i = 0; i < list.size(); i++){
            if(list.get(i).getTilCnt() == myRanking.getTilCnt()){
                break;
            }
            if (list.get(i).getTilCnt() > myRanking.getTilCnt()){
                myRank += 1;
            }
        }
        return RankingResponse.TilRankList.build(myRank, user, myRanking.getTilCnt(), tilRankList);
    }

    public RankingResponse.TilRankList getMyTilRank(int userId, int page){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Follow> followingList = followRepository.findByFollowing(userId);
        List<Ranking> followRankList = new ArrayList<>();
        Ranking myRanking = rankingRepository.findByUser_Id(userId);
        followRankList.add(myRanking);
        List<RankingResponse.TilRank> tilRankList = new ArrayList<>();
        for (int i = 0; i < followingList.size(); i++){
            int followingId = followingList.get(i).getFromId();
            followRankList.add(rankingRepository.findByUser_Id(followingId));
        }
        Collections.sort(followRankList, new Comparator<Ranking>() {
            @Override
            public int compare(Ranking o1, Ranking o2) {
                return o2.getTilCnt()-o1.getTilCnt();
            }
        });
        int startIndex = 20*(page-1);
        int endIndex = 20*page;
        if(endIndex > followRankList.size()){
            endIndex = followingList.size();
        }
        for (int i = startIndex; i < endIndex; i++){
            int rank = i+1;
            Ranking ranking = followRankList.get(i);
            tilRankList.add(RankingResponse.TilRank.build(rank, ranking.getUser(), ranking.getTilCnt()));
        }
        int myRank = 1;
        for (int i = 0; i < followRankList.size(); i++){
            if(followRankList.get(i).getTilCnt() == myRanking.getTilCnt()){
                break;
            }
            if(followRankList.get(i).getTilCnt() > myRanking.getTilCnt()){
                myRank += 1;
            }
        }
        return RankingResponse.TilRankList.build(myRank, user, myRanking.getTilCnt(), tilRankList);
    }

}
