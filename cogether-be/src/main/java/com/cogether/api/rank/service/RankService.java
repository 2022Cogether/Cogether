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
        Ranking myRanking = rankingRepository.findByUser_Id(userId);
        int startIndex = 20*(page-1);
        int endIndex = 20*(page);
        if(endIndex > list.size()) {
            endIndex = list.size();
        }
        List<RankingResponse.TilRank> tilRankList = new ArrayList<>();
        for (int i = startIndex; i < endIndex; i++){
            int rank = 1;
            Ranking ranking = list.get(i);
            for (int j = 0; j < list.size(); j++){
                if(list.get(j).getTilCnt() == ranking.getTilCnt()){
                    break;
                }
                if(list.get(j).getTilCnt() > ranking.getTilCnt()){
                    rank += 1;
                }
            }
            tilRankList.add(RankingResponse.TilRank.build(rank, ranking.getUser(), ranking.getTilCnt()));
        }
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
            endIndex = followRankList.size();
        }
        for (int i = startIndex; i < endIndex; i++){
            int rank = 1;
            Ranking ranking = followRankList.get(i);
            for (int j = 0; j < followRankList.size(); j++){
                if(followRankList.get(j).getTilCnt() == ranking.getTilCnt()){
                    break;
                }
                if(followRankList.get(j).getTilCnt() > ranking.getTilCnt()){
                    rank += 1;
                }
            }
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

    public RankingResponse.ExpRankList getExpRank(int userId, int page){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<User> list = userRepository.findAllByOrderByExp();
        int startIndex = 20*(page-1);
        int endIndex = 20*(page);
        if(endIndex > list.size()) {
            endIndex = list.size();
        }
        List<RankingResponse.ExpRank> expRankList = new ArrayList<>();
        for (int i = startIndex; i < endIndex; i++){
            User u = list.get(i);
            int rank = 1;
            for (int j = 0; j < list.size(); j++){
                if(list.get(j).getExp() == u.getExp()){
                    break;
                }
                if(list.get(j).getExp() > u.getExp()){
                    rank += 1;
                }
            }
            expRankList.add(RankingResponse.ExpRank.build(rank, u));
        }
        int myRank = 1;
        for (int i = 0; i < list.size(); i++){
            if(list.get(i).getExp() == user.getExp()){
                break;
            }
            if(list.get(i).getExp() > user.getExp()){
                myRank += 1;
            }
        }
        return RankingResponse.ExpRankList.build(myRank, user, expRankList);
    }

    public RankingResponse.ExpRankList getMyExpRank(int userId, int page){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Follow> followList = followRepository.findByFollowing(userId);
        List<User> followUserList = new ArrayList<>();
        followUserList.add(user);
        List<RankingResponse.ExpRank> expRankList = new ArrayList<>();
        for (int i = 0; i < followUserList.size(); i++){
            int followId = followList.get(i).getFromId();
            followUserList.add(userRepository.findById(followId).orElseThrow(UserNotFoundException::new));
        }
        Collections.sort(followUserList, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o2.getExp()-o1.getExp();
            }
        });
        int startIndex = 20*(page-1);
        int endIndex = 20*page;
        if(endIndex > followUserList.size()){
            endIndex = followUserList.size();
        }
        for (int i = startIndex; i < endIndex; i++){
            int rank = 1;
            User u = followUserList.get(i);
            for (int j = 0; j < followUserList.size(); j++){
                if(followUserList.get(j).getExp() == u.getExp()){
                    break;
                }
                if(followUserList.get(j).getExp() > u.getExp()){
                    rank += 1;
                }
            }
            expRankList.add(RankingResponse.ExpRank.build(rank, u));
        }
        int myRank = 1;
        for (int i = 0; i < followUserList.size(); i++){
            if(followUserList.get(i).getExp() == user.getExp()){
                break;
            }
            if(followUserList.get(i).getExp() > user.getExp()){
                myRank += 1;
            }
        }
        return RankingResponse.ExpRankList.build(myRank, user, expRankList);
    }

    public RankingResponse.LiveCompRankList getTotalRank(int userId, int page){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Ranking> list = rankingRepository.findAllByOrderByTotalDesc();
        List<RankingResponse.LiveCompRank> totalRankList = new ArrayList<>();
        Ranking myRanking = rankingRepository.findByUser_Id(userId);
        int startIndex = 20*(page-1);
        int endIndex = 20*page;
        if(endIndex > list.size()){
            endIndex = list.size();
        }
        for (int i = startIndex; i < endIndex; i++){
            int rank = 1;
            Ranking ranking = list.get(i);
            for (int j = 0; j < list.size(); j++){
                if(list.get(j).getTotal() == ranking.getTotal()){
                    break;
                }
                if(list.get(j).getTotal() > ranking.getTotal()){
                    rank += 1;
                }
            }
            totalRankList.add(RankingResponse.LiveCompRank.build(rank, ranking.getUser(), ranking.getTotal()));
        }
        int myRank = 1;
        for (int i = 0; i < list.size(); i++){
            if(list.get(i).getTotal() == myRanking.getTotal()){
                break;
            }
            if(list.get(i).getTotal() > myRanking.getTotal()){
                myRank += 1;
            }
        }
        return RankingResponse.LiveCompRankList.build(myRank, user, myRanking.getTotal(), totalRankList);
    }

    public RankingResponse.LiveCompRankList getMyTotalRank(int userId, int page){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Follow> followList = followRepository.findByFollowing(userId);
        List<Ranking> followRankList = new ArrayList<>();
        Ranking myRanking = rankingRepository.findByUser_Id(userId);
        followRankList.add(myRanking);
        List<RankingResponse.LiveCompRank> totalRankList = new ArrayList<>();
        for (int i = 0; i < followList.size(); i++){
            int followId = followList.get(i).getFromId();
            followRankList.add(rankingRepository.findByUser_Id(followId));
        }
        Collections.sort(followRankList, new Comparator<Ranking>() {
            @Override
            public int compare(Ranking o1, Ranking o2) {
                return o2.getTotal()-o1.getTotal();
            }
        });
        int startIndex = 20*(page-1);
        int endIndex = 20 * page;
        if(endIndex > followRankList.size()){
            endIndex = followRankList.size();
        }
        for (int i = startIndex; i < endIndex; i++){
            int rank = 1;
            Ranking ranking = followRankList.get(i);
            for (int j = 0; j < followRankList.size(); j++){
                if(followRankList.get(j).getTotal() == ranking.getTotal()){
                    break;
                }
                if(followRankList.get(j).getTotal() > ranking.getTotal()){
                    rank += 1;
                }
            }
            totalRankList.add(RankingResponse.LiveCompRank.build(rank, ranking.getUser(), ranking.getTotal()));
        }
        int myRank = 1;
        for (int i = 0; i < followRankList.size(); i++){
            if(followRankList.get(i).getTotal() == myRanking.getTotal()){
                break;
            }
            if(followRankList.get(i).getTotal() > myRanking.getTotal()){
                myRank += 1;
            }
        }
        return RankingResponse.LiveCompRankList.build(myRank, user, myRanking.getTotal(), totalRankList);
    }

    public RankingResponse.LiveCompRankList getWeekRank(int userId, int page){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Ranking> list = rankingRepository.findAllByOrderByWeekDesc();
        List<RankingResponse.LiveCompRank> weekRankList = new ArrayList<>();
        Ranking myRanking = rankingRepository.findByUser_Id(userId);
        int startIndex = 20*(page-1);
        int endIndex = 20*page;
        if(endIndex > list.size()){
            endIndex = list.size();
        }
        for (int i = startIndex; i < endIndex; i++){
            int rank = 1;
            Ranking ranking = list.get(i);
            for (int j = 0; j < list.size(); j++){
                if(list.get(j).getWeek() == ranking.getWeek()){
                    break;
                }
                if(list.get(j).getWeek() > ranking.getWeek()){
                    rank += 1;
                }
            }
            weekRankList.add(RankingResponse.LiveCompRank.build(rank, ranking.getUser(), ranking.getWeek()));
        }
        int myRank = 1;
        for (int i = 0; i < list.size(); i++){
            if(list.get(i).getWeek() == myRanking.getWeek()){
                break;
            }
            if(list.get(i).getWeek() > myRanking.getWeek()){
                myRank += 1;
            }
        }
        return RankingResponse.LiveCompRankList.build(myRank, user, myRanking.getWeek(), weekRankList);
    }

    public RankingResponse.LiveCompRankList getMyWeekRank(int userId, int page){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Follow> followList = followRepository.findByFollowing(userId);
        List<Ranking> followRankList = new ArrayList<>();
        Ranking myRanking = rankingRepository.findByUser_Id(userId);
        followRankList.add(myRanking);
        List<RankingResponse.LiveCompRank> weekRankList = new ArrayList<>();
        for (int i = 0; i < followList.size(); i++){
            int followId = followList.get(i).getFromId();
            followRankList.add(rankingRepository.findByUser_Id(followId));
        }
        Collections.sort(followRankList, new Comparator<Ranking>() {
            @Override
            public int compare(Ranking o1, Ranking o2) {
                return o2.getWeek()-o1.getWeek();
            }
        });
        int startIndex = 20*(page-1);
        int endIndex = 20 * page;
        if(endIndex > followRankList.size()){
            endIndex = followRankList.size();
        }
        for (int i = startIndex; i < endIndex; i++){
            int rank = 1;
            Ranking ranking = followRankList.get(i);
            for (int j = 0; j < followRankList.size(); j++){
                if(followRankList.get(j).getWeek() == ranking.getWeek()){
                    break;
                }
                if(followRankList.get(j).getWeek() > ranking.getWeek()){
                    rank += 1;
                }
            }
            weekRankList.add(RankingResponse.LiveCompRank.build(rank, ranking.getUser(), ranking.getWeek()));
        }
        int myRank = 1;
        for (int i = 0; i < followRankList.size(); i++){
            if(followRankList.get(i).getWeek() == myRanking.getWeek()){
                break;
            }
            if(followRankList.get(i).getWeek() > myRanking.getWeek()){
                myRank += 1;
            }
        }
        return RankingResponse.LiveCompRankList.build(myRank, user, myRanking.getWeek(), weekRankList);
    }

    public RankingResponse.LiveCompRankList getMonthRank(int userId, int page){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Ranking> list = rankingRepository.findAllByOrderByMonthDesc();
        List<RankingResponse.LiveCompRank> monthRankList = new ArrayList<>();
        Ranking myRanking = rankingRepository.findByUser_Id(userId);
        int startIndex = 20*(page-1);
        int endIndex = 20*page;
        if(endIndex > list.size()){
            endIndex = list.size();
        }
        for (int i = startIndex; i < endIndex; i++){
            int rank = 1;
            Ranking ranking = list.get(i);
            for (int j = 0; j < list.size(); j++){
                if(list.get(j).getMonth() == ranking.getMonth()){
                    break;
                }
                if(list.get(j).getMonth() > ranking.getMonth()){
                    rank += 1;
                }
            }
            monthRankList.add(RankingResponse.LiveCompRank.build(rank, ranking.getUser(), ranking.getMonth()));
        }
        int myRank = 1;
        for (int i = 0; i < list.size(); i++){
            if(list.get(i).getMonth() == myRanking.getMonth()){
                break;
            }
            if(list.get(i).getMonth() > myRanking.getMonth()){
                myRank += 1;
            }
        }
        return RankingResponse.LiveCompRankList.build(myRank, user, myRanking.getMonth(), monthRankList);
    }

    public RankingResponse.LiveCompRankList getMyMonthRank(int userId, int page){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Follow> followList = followRepository.findByFollowing(userId);
        List<Ranking> followRankList = new ArrayList<>();
        Ranking myRanking = rankingRepository.findByUser_Id(userId);
        followRankList.add(myRanking);
        List<RankingResponse.LiveCompRank> monthRankList = new ArrayList<>();
        for (int i = 0; i < followList.size(); i++){
            int followId = followList.get(i).getFromId();
            followRankList.add(rankingRepository.findByUser_Id(followId));
        }
        Collections.sort(followRankList, new Comparator<Ranking>() {
            @Override
            public int compare(Ranking o1, Ranking o2) {
                return o2.getMonth()-o1.getMonth();
            }
        });
        int startIndex = 20*(page-1);
        int endIndex = 20 * page;
        if(endIndex > followRankList.size()){
            endIndex = followRankList.size();
        }
        for (int i = startIndex; i < endIndex; i++){
            int rank = 1;
            Ranking ranking = followRankList.get(i);
            for (int j = 0; j < followRankList.size(); j++){
                if(followRankList.get(j).getMonth() == ranking.getMonth()){
                    break;
                }
                if(followRankList.get(j).getMonth() > ranking.getMonth()){
                    rank += 1;
                }
            }
            monthRankList.add(RankingResponse.LiveCompRank.build(rank, ranking.getUser(), ranking.getMonth()));
        }
        int myRank = 1;
        for (int i = 0; i < followRankList.size(); i++){
            if(followRankList.get(i).getMonth() == myRanking.getMonth()){
                break;
            }
            if(followRankList.get(i).getMonth() > myRanking.getMonth()){
                myRank += 1;
            }
        }
        return RankingResponse.LiveCompRankList.build(myRank, user, myRanking.getMonth(), monthRankList);
    }
}
