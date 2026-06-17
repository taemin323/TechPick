package com.techpick.backend.user.service;

import com.techpick.backend.user.entity.User;
import com.techpick.backend.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("새로운 UUID가 들어오면 유저를 생성하고 저장한다")
    void createUser() {
        //Given
        String userUuid =  UUID.randomUUID().toString();
        given(userRepository.findByUserUuid(userUuid)).willReturn(Optional.empty());
        given(userRepository.save(any(User.class))).willAnswer(invocation -> invocation.getArgument(0));

        //When
        User user = userService.findOrCreateUser(userUuid);

        //Then
        assertThat(user.getUserUuid()).isEqualTo(userUuid);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("UUID 없이 요청하면 서버가 UUID를 생성해서 저장한다")
    void updateUser() {

        //Given
        String userUuid = null;
        User savedUser = User.builder()
                .userUuid(UUID.randomUUID().toString())
                .build();

        given(userRepository.save(any(User.class))).willReturn(savedUser);

        //When
        User result = userService.findOrCreateUser(userUuid);

        //Then
        verify(userRepository, never()).findByUserUuid(any());
        verify(userRepository, times(1)).save(any());
        assertThat(result.getUserUuid()).isNotNull();
    }
}
