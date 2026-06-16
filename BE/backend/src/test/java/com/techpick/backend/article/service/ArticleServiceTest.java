package com.techpick.backend.article.service;

import com.techpick.backend.article.dto.request.ArticleRequest;
import com.techpick.backend.article.dto.response.ArticleListResponse;
import com.techpick.backend.article.dto.response.ArticleResponse;
import com.techpick.backend.article.entity.Article;
import com.techpick.backend.article.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("아티클 생성 요청을 받으면 DB에 저장하고 응답을 반환한다")
    void createArticle() {
        //Given
        ArticleRequest request = new ArticleRequest("테스트 제목", "테스트 내용", "https://techpick.com", "2026-02-10", "네이버", "https://thumb.com");
        Article article = Article.builder()
                .articleId(1L)
                .title("제목")
                .url("https://url.com")
                .build();

        given(articleRepository.save(any(Article.class))).willReturn(article);

        // When
        ArticleResponse response = articleService.createArticle(request);

        // Then
        assertThat(response.getArticleId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("제목");
        verify(articleRepository).save(any(Article.class));
    }

    @Test
    @DisplayName("아티클 목록을 조회하면 페이징 정보가 포함된 DTO를 반환한다")
    void getAllArticles() {
        //Given
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Article article = Article.builder()
                .articleId(1L)
                .title("제목")
                .url("https://test.com")
                .content("내용")
                .blogName("테스트 블로그")
                .pubDate("2026-02-03")
                .thumbnailUrl("https://thumb.com")
                .build();

        //Mock Repository가 Page 객체를 반환하도록 설정
        Page<Article> articlePage = new PageImpl<>(List.of(article), pageRequest, 1);
        given(articleRepository.findAll(any(PageRequest.class))).willReturn(articlePage);

        //When
        ArticleListResponse response = articleService.getAllArticles(page, size);

        //Then
        assertThat(response.getArticles()).hasSize(1);
        assertThat(response.getListSize()).isEqualTo(1);
        assertThat(response.getTotalPages()).isEqualTo(1);
        assertThat(response.isFirst()).isTrue();
        assertThat(response.getArticles().get(0).getTitle()).isEqualTo("제목");

        // Repository 호출 여부 검증
        verify(articleRepository, times(1)).findAll(any(PageRequest.class));
    }
}
