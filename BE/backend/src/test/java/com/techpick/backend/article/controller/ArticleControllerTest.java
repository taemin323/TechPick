package com.techpick.backend.article.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techpick.backend.article.dto.request.ArticleRequest;
import com.techpick.backend.article.dto.response.ArticleResponse;
import com.techpick.backend.article.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArticleController.class)
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ArticleService articleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    @DisplayName("새로운 아티클을 생성하면 성공을 응답한다")
    void createArticle() throws Exception {
        //Given
        ArticleRequest request = new ArticleRequest("테스트 제목", "테스트 내용", "https://techpick.com");
        ArticleResponse response = new ArticleResponse(1L, "테스트 제목", "https://techpick.com", "내용", "2026-02-02", "네이버", "https://thumbnail.com");

        given(articleService.createArticle(any(ArticleRequest.class))).willReturn(response);

        // When & Then
        mockMvc.perform(post("/api/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf()))
        .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.result.title").value("테스트 제목"))
                .andExpect(jsonPath("$.result.articleId").value(1L));
    }

    @Test
    @WithMockUser
    @DisplayName("아티클 상세 조회하면 성공을 응답한다")
    void getArticleDetail() throws Exception {
        //Given
        Long articleId = 1L;
        ArticleResponse response = ArticleResponse.builder()
                .articleId(articleId)
                .title("제목1")
                .url("https://techpick1.com")
                .content("내용1")
                .pubDate("2026-02-02")
                .blogName("네이버")
                .thumbnailUrl("https://thumbnail1.com")
                .build();

        // articleService.getArticleDetail(id)가 호출되면 위 response를 반환하도록 약속(Mocking)
        given(articleService.getArticleDetail(articleId)).willReturn(response);

        //When & Then
        mockMvc.perform(get("/api/articles/" + articleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("ARTICLE2001"))
                .andExpect(jsonPath("$.result.title").value("제목1"))
                .andExpect(jsonPath("$.result.articleId").value(articleId));
    }

}
