package com.techpick.backend.article.service;

import com.techpick.backend.article.dto.request.ArticleRequest;
import com.techpick.backend.article.dto.response.ArticleResponse;
import com.techpick.backend.article.entity.Article;
import com.techpick.backend.article.repository.ArticleRepository;
import com.techpick.backend.common.apiPayload.code.status.ErrorStatus;
import com.techpick.backend.common.apiPayload.exception.GeneralException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleResponse createArticle(ArticleRequest request) {

        Article article = Article.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .url(request.getUrl())
                .build();

        Article savedArticle = articleRepository.save(article);

        return ArticleResponse.builder()
                .articleId(savedArticle.getArticleId())
                .title(savedArticle.getTitle())
                .url(savedArticle.getUrl())
                .build();
    }

    public ArticleResponse getArticleDetail(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ARTICLE_NOT_FOUND));
        return ArticleResponse.from(article);
    }
}
