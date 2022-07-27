package com.cogether.api.liveCoop.repository;

import com.cogether.api.liveCoop.domain.LiveCoop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiveCoopRepository extends JpaRepository<LiveCoop, Integer> {

}
