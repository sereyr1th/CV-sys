package com.cvmaker.cvmaker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cvmaker.cvmaker.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
