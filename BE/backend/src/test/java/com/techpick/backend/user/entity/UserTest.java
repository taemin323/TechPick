package com.techpick.backend.user.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    @Test
    @DisplayName("UUID를 가진 사용자를 정상적으로 생성할 수 있다")
    void createUser() {

        // Given
        String userUuid = UUID.randomUUID().toString();

        // When
        User user = new User(userUuid);

        // Then
        assertThat(user.getUserUuid()).isEqualTo(userUuid);
    }
}
