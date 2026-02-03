package com.techpick.backend.user.service;

import com.techpick.backend.user.entity.User;
import com.techpick.backend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public User findOrCreateUser(String userUuid) {
        return userRepository.findByUserUuid(userUuid)
                .orElseGet(() -> userRepository.save(new User(userUuid)));
    }
}
