package com.techpick.backend.user.repository;

import com.techpick.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserUuid(String uuid);
}
