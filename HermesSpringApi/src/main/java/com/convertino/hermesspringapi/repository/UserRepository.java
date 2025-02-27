package com.convertino.hermesspringapi.repository;

import com.convertino.hermesspringapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByPhoneNumber(String phoneNumber);

    boolean existsByUsername(String email);

    void deleteByUsername(String email);
}
