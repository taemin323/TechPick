package com.techpick.backend.bookmark.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techpick.backend.bookmark.dto.BookmarkToggleResponse;
import com.techpick.backend.bookmark.service.BookmarkService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookmarkController.class)
public class BookmarkControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookmarkService bookmarkService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser// 시큐리티 설정 때문에
    @DisplayName("북마크 토글 API가 성공적으로 응답을 반환한다")
    void toggleBookmark() throws Exception {
        //Given
        Long articleId = 1L;
        String userUuid = "test-uuid";
        BookmarkToggleResponse response = new BookmarkToggleResponse("ADDED", LocalDateTime.now(ZoneOffset.UTC));

        given(bookmarkService.toggleBookmark(anyString(), anyLong())).willReturn(response);

        //When & Then
        mockMvc.perform(post("/api/bookmarks/" + articleId)
                .header("X-USER-ID", userUuid) // 유저 식별을 헤더로 받는다고 가정
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.result.action").value("ADDED"));
    }
}
