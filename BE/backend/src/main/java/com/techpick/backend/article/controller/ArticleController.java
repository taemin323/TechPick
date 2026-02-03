package com.techpick.backend.article.controller;

import com.techpick.backend.article.dto.request.ArticleRequest;
import com.techpick.backend.article.dto.response.ArticleResponse;
import com.techpick.backend.article.service.ArticleService;
import com.techpick.backend.common.apiPayload.ApiResponse;
import com.techpick.backend.common.apiPayload.code.status.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ApiResponse<ArticleResponse> createArticle(@RequestBody ArticleRequest request){
        ArticleResponse response = articleService.createArticle(request);

        return ApiResponse.onSuccess(response);
    }

    @GetMapping("/{articleId}")
    public ApiResponse<ArticleResponse> getArticleDetail(@PathVariable Long articleId){
        ArticleResponse response = articleService.getArticleDetail(articleId);
        return ApiResponse.of(SuccessStatus.ARTICLE_DETAIL_SUCCESS, response);
    }

}
