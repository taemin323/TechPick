package com.techpick.backend.article.controller;

import com.techpick.backend.article.dto.request.ArticleRequest;
import com.techpick.backend.article.dto.response.ArticleListResponse;
import com.techpick.backend.article.dto.response.ArticleResponse;
import com.techpick.backend.article.service.ArticleService;
import com.techpick.backend.common.apiPayload.ApiResponse;
import com.techpick.backend.common.apiPayload.code.status.SuccessStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    /**
     * 아티클 생성
     * @param request
     * @return
     */
    @PostMapping
    public ApiResponse<ArticleResponse> createArticle(@RequestBody ArticleRequest request){
        ArticleResponse response = articleService.createArticle(request);

        return ApiResponse.onSuccess(response);
    }

    /**
     * 아티클 상세 조회
     * @param articleId
     * @return
     */
    @GetMapping("/{articleId}")
    public ApiResponse<ArticleResponse> getArticleDetail(@PathVariable Long articleId){
        ArticleResponse response = articleService.getArticleDetail(articleId);
        return ApiResponse.of(SuccessStatus.ARTICLE_DETAIL_SUCCESS, response);
    }

    @GetMapping
    public ApiResponse<ArticleListResponse> getAllArticles(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){
        // Service를 호출하여 페이징된 결과 가져오기
        ArticleListResponse response = articleService.getAllArticles(page, size);

        return ApiResponse.of(SuccessStatus.ARTICLE_LIST_SUCCESS, response);
    }

}
