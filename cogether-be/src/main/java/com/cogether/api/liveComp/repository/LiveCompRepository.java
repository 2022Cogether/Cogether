package com.cogether.api.liveComp.repository;

import com.cogether.api.liveComp.domain.LiveComp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiveCompRepository extends JpaRepository<LiveComp, Integer> {

}
