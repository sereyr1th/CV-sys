package com.cvmaker.cvmaker.repository;

import com.cvmaker.cvmaker.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByEmail(String email);
    User findByEmail(String email);
}