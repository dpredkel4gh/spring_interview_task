package com.yellow.test.repository;

import com.yellow.test.entity.Running;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RunningRepository extends JpaRepository<Running, Long> {

    @Query("select r from Running r join r.user u where u.uuid = ?1")
    Page<Running> findByUserUuid(String userUuid, Pageable pageable);

    @Query("select r from Running r join r.user u where r.uuid = :uuid and u.uuid = :userUuid")
    Running findByUuidAndUserUuid(@Param("uuid") String uuid, @Param("userUuid") String userUuid);

    @Query("select r from Running r join r.user u where r.date >= :begin and r.date <= :end and u.uuid = :userUuid")
    List<Running> findByDateIntervalAndUserUuid(@Param("begin") LocalDate begin, @Param("end") LocalDate end, @Param("userUuid") String userUuid);

}
