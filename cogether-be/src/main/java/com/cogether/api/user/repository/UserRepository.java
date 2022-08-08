package com.cogether.api.user.repository;

import com.cogether.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Integer countAllByCompIsTrue();

    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickName);


    Optional<User> findById(int userId);
}
