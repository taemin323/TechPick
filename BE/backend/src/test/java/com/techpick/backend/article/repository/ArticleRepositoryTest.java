package com.techpick.backend.article.repository;

import com.techpick.backend.article.entity.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("아티클을 저장하면 생성된 ID를 확인할 수 있다")
    void saveArticle() {
        //Given
        Article article = Article.builder()
                .title("DB 테스트 제목")
                .content("DB 테스트 내용")
                .url("https://db-test.com")
                .pubDate("2026-01-30")
                .blogName("DB 테스트 블로그")
                .thumbnailUrl("http://image.com/1")
                .build();

        //When
        Article savedArticle = articleRepository.save(article);

        //Then
        assertThat(savedArticle.getArticleId()).isNotNull();
        assertThat(savedArticle.getTitle()).isEqualTo("DB 테스트 제목");
        assertThat(savedArticle.getUrl()).isEqualTo("https://db-test.com");
    }

    @Test
    @DisplayName("이미 존재하는 URL로 저장 시 에러가 발생한다")
    void saveDuplicateUrl() {
        //Given
        Article article1 = Article.builder()
                .title("제목1").content("내용1").url("https://same.com").blogName("블로그1").pubDate("2026-01-30").thumbnailUrl("http://image.com/1").build();
        articleRepository.save(article1);

        Article article2 = Article.builder()
                .title("제목2").content("내용2").url("https://same.com").blogName("블로그2").pubDate("2026-01-30").thumbnailUrl("http://image.com/2").build();

        //When & Then
        assertThrows(Exception.class, () -> {
           articleRepository.save(article2);
        });
    }
}
