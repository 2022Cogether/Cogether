package com.cogether.api.user.repository;

import com.cogether.api.user.domain.Auth;
import com.cogether.api.user.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Integer> {

    List<Follow> findById(int to_id);
}
