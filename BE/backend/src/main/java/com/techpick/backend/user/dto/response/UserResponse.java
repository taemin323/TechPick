package com.techpick.backend.user.dto.response;

import com.techpick.backend.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private String userUuid;

    // Entity를 DTO로 변환하는 정적 팩토리 메서드
    public static UserResponse from(User user) {
        return UserResponse.builder()
                .userUuid(user.getUserUuid())
                .build();
    }
}
