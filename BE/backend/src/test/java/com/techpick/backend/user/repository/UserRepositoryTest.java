package com.techpick.backend.user.repository;

import com.techpick.backend.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("저장된 UUID로 사용자를 조회할 수 있다")
    void saveUserTest() {
        // Given
        String uuid = UUID.randomUUID().toString();
        userRepository.save(new User(uuid));

        // When
        User savedUser = userRepository.findByUserUuid(uuid)
                .orElseThrow();

        // Then
        assertThat(savedUser.getUserUuid()).isEqualTo(uuid);
    }
}
