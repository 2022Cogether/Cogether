package com.cogether.api.Noti.repository;

import com.cogether.api.Noti.domain.Noti;
import com.cogether.api.follow.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotiRepository extends JpaRepository<Noti, Integer> {
}
