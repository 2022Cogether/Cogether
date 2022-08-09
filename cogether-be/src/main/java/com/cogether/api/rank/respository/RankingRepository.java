package com.cogether.api.rank.respository;

import com.cogether.api.rank.domain.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankingRepository extends JpaRepository<Ranking, Integer> {
}
