package com.yellow.test.repository;

import com.yellow.test.entity.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("select i from Image i join i.user u where u.uuid = ?1")
    Page<Image> findByUserUuid(String userUuid, Pageable pageable);

    @Query("select i from Image i join i.user u where i.uuid = :uuid and u.uuid = :userUuid")
    Image findByUuidAndUserUuid(@Param("uuid") String uuid, @Param("userUuid") String userUuid);

}
