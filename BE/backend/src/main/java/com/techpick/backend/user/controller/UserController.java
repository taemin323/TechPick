package com.techpick.backend.user.controller;

import com.techpick.backend.common.apiPayload.code.status.SuccessStatus;
import com.techpick.backend.user.dto.request.UserRequest;
import com.techpick.backend.user.dto.response.UserResponse;
import com.techpick.backend.user.service.UserService;
import com.techpick.backend.common.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> login(@RequestBody UserRequest request) {
        UserResponse response = UserResponse.from(userService.findOrCreateUser(request.getUserUuid()));

        return ApiResponse.of(SuccessStatus.USER_INFO_SUCCESS, response);
    }

}
