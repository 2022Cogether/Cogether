package com.cogether.api.user.repository;

import com.cogether.api.user.domain.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Integer> {

    Optional<Auth> findByUserId(int userId);
}
