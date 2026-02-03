package com.techpick.backend.article.service;

import com.techpick.backend.article.dto.request.ArticleRequest;
import com.techpick.backend.article.dto.response.ArticleListResponse;
import com.techpick.backend.article.dto.response.ArticleResponse;
import com.techpick.backend.article.entity.Article;
import com.techpick.backend.article.repository.ArticleRepository;
import com.techpick.backend.common.apiPayload.code.status.ErrorStatus;
import com.techpick.backend.common.apiPayload.exception.GeneralException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;

    /**
     * 아티클 생성
     * @param request
     * @return
     */
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

    /**
     * 아티클 상세 조회
     * @param articleId
     * @return
     */
    public ArticleResponse getArticleDetail(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ARTICLE_NOT_FOUND));
        return ArticleResponse.from(article);
    }

    /**
     * 아티클 목록 조회
     * @param page
     * @param size
     * @return
     */
    public ArticleListResponse getAllArticles(int page, int size) {
        // 페이징 및 정렬 설정 (최신순)
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());

        // DB에서 페이징된 데이터 가져오기
        Page<Article> articlePage = articleRepository.findAll(pageRequest);

        // Entity 리스트를 Response DTO 리스트로 변환
        List<ArticleResponse> articleResponses = articlePage.getContent().stream()
                .map(ArticleResponse::from)
                .collect(Collectors.toList());

        // DTO 구조에 맞춰 변환
        return ArticleListResponse.builder()
                .articles(articleResponses)
                .listSize(articleResponses.size())
                .totalPages(articlePage.getTotalPages())
                .totalElements(articlePage.getTotalElements())
                .isFirst(articlePage.isFirst())
                .isLast(articlePage.isLast())
                .build();
    }
}
