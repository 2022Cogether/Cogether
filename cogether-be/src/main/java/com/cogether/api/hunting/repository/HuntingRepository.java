package com.cogether.api.hunting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cogether.api.hunting.dto.Hunting;
public interface HuntingRepository extends JpaRepository<Hunting, Integer> {

}
