package com.cogether.api.rank.respository;

import com.cogether.api.rank.domain.Ranking;
import com.cogether.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RankingRepository extends JpaRepository<Ranking, Integer> {
    Ranking findByUser(User user);
    Ranking findByUser_Id(int userId);

    List<Ranking> findAllByOrderByTilCntDesc();
    List<Ranking> findAllByOrderByTotalDesc();
//    List<Ranking> findAllByOrderByWeekDesc();
//    List<Ranking> findAllByOrderByMonthDesc();
}
