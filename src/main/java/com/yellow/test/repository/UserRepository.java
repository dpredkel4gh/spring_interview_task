package com.yellow.test.repository;

import com.yellow.test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.uuid = ?1")
    User findByUuid(String uuid);

    @Query("select u from User u where u.username = ?1")
    User findByUsername(String username);

}
