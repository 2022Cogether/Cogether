package com.cogether.api.liveCoop.repository;

import com.cogether.api.liveCoop.domain.LiveCoop;
import com.cogether.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LiveCoopRepository extends JpaRepository<LiveCoop, Integer> {
    List<LiveCoop> findAllByOrderByCreatedAtDesc();

    LiveCoop findByUser(User user);

    List<LiveCoop> findAllByIdNotOrderByCreatedAtDesc(int id);
}
