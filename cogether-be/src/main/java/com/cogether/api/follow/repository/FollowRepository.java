package com.cogether.api.follow.repository;

import com.cogether.api.follow.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Integer> {

    List<Follow> findById(int toId);
    @Query("select f from Follow f where f.user.id=:to_id")
    List<Follow> findByFollowing(@Param("to_id") int to_id);

    @Query("select f from Follow f where f.fromId =:toId ")
    List<Follow> findByFollower(@Param("toId") int toId);

    @Query("select f.id from Follow f where f.fromId =:fromId AND f.user.id =:toId")
    Optional<Follow> findFollowId(@Param("toId") int toId, @Param("fromId") int fromId);

    @Transactional
    @Modifying
    @Query("delete from Follow f where f.fromId =:fromId AND f.user.id=:toId")
    void deleteByIdInQuery(@Param("toId") int toId,@Param("fromId") int fromId);

}
