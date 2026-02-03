package com.techpick.backend.article.service;

import com.techpick.backend.article.dto.request.ArticleRequest;
import com.techpick.backend.article.dto.response.ArticleResponse;
import com.techpick.backend.article.entity.Article;
import com.techpick.backend.article.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("아티클 생성 요청을 받으면 DB에 저장하고 응답을 반환한다")
    void createArticle() {
        //Given
        ArticleRequest request = new ArticleRequest("제목", "내용" , "https://url.com");
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
}
