package com.cogether.api.liveComp.repository;

import com.cogether.api.liveComp.domain.LiveComp;
import com.cogether.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LiveCompRepository extends JpaRepository<LiveComp, Integer> {
    LiveComp findByCreatedAtBetweenAndUser(LocalDateTime startDatetime, LocalDateTime endDatetime, User user);

    Integer countAllByCreatedAtBetweenAndUser(LocalDateTime startDatetime, LocalDateTime endDatetime, User user);

    List<LiveComp> findAllByCreatedAtBetweenOrderByTotalTimeDesc(LocalDateTime startDatetime, LocalDateTime endDatetime);
}
