package com.cogether.api.liveComp.repository;

import com.cogether.api.liveComp.domain.LiveComp;
import com.cogether.api.user.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface LiveCompRepository extends JpaRepository<LiveComp, Integer> {
    LiveComp findByCreatedAtBetweenAndUser(LocalDateTime startDatetime, LocalDateTime endDatetime, User user);

    Integer countAllByCreatedAtBetweenAndUser(LocalDateTime startDatetime, LocalDateTime endDatetime, User user);
}
