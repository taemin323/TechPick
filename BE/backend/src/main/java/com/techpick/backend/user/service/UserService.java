package com.techpick.backend.user.service;

import com.techpick.backend.user.entity.User;
import com.techpick.backend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public User findOrCreateUser(String userUuid) {
        if (userUuid == null || userUuid.isBlank()) {
            return userRepository.save(new User(UUID.randomUUID().toString()));
        }
        return userRepository.findByUserUuid(userUuid)
                .orElseGet(() -> userRepository.save(new User(userUuid)));
    }
}
