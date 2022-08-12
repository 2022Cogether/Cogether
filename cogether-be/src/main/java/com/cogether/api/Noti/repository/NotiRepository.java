package com.cogether.api.Noti.repository;

import com.cogether.api.Noti.domain.Noti;
import com.cogether.api.follow.domain.Follow;
import com.cogether.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotiRepository extends JpaRepository<Noti, Integer> {

    List<Noti> findAllByUser(User user);
    Optional<Noti> findByContentId(int contentId);

}
