package com.cvmaker.cvmaker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cvmaker.cvmaker.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}