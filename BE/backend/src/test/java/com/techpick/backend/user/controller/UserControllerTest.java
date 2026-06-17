package com.techpick.backend.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper; // 경로 확인!
import com.techpick.backend.user.dto.request.UserRequest;
import com.techpick.backend.user.entity.User;
import com.techpick.backend.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType; // Spring의 MediaType 사용!
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    @DisplayName("UUID를 전달하면 유저 정보를 반환한다")
    void login() throws Exception {
        // Given
        String uuid = UUID.randomUUID().toString();
        UserRequest request = new UserRequest(uuid); // 생성자에 uuid를 넣도록 DTO 수정 필요
        User user = new User(uuid);

        given(userService.findOrCreateUser(uuid)).willReturn(user);

        // When & Then
        mockMvc.perform(
                        post("/api/users") // 1. post() 시작
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .with(csrf())
                )
                .andExpect(status().isOk()) // 3. 그 뒤에 .andExpect가 붙어야 함
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("USER2001"))
                .andExpect(jsonPath("$.result.userUuid").value(uuid));


    }

    @Test
    @WithMockUser
    @DisplayName("UUID 없이 요청하면 서버가 생성한 UUID를 응답한다")
    void loginWithoutUuid() throws Exception {

        //Given
        String newUuid = UUID.randomUUID().toString();
        User user = new User(newUuid);
        UserRequest request = new UserRequest(null);

        given(userService.findOrCreateUser(null)).willReturn(user);

        //When & Then
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.result.userUuid").isNotEmpty());
    }
}