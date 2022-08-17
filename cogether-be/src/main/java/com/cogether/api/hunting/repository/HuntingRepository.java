package com.cogether.api.hunting.repository;

import com.cogether.api.hunting.domain.Hunting;
import com.cogether.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HuntingRepository extends JpaRepository<Hunting, Integer> {
    List<Hunting> findAllByOrderByIdDesc();

    List<Hunting> findAllByUserOrderByIdDesc(User user);
}
