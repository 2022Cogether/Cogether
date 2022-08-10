package com.cogether.api.hunting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cogether.api.hunting.domain.Hunting;

import java.util.List;

public interface HuntingRepository extends JpaRepository<Hunting, Integer> {
    List<Hunting> findAllByOrderByIdDesc();
}
