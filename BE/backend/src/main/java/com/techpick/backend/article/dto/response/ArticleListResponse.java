package com.techpick.backend.article.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ArticleListResponse {
    private List<ArticleResponse> articles;
    private int listSize;
    private int totalPages;
    private long totalElements;
    private boolean isFirst;
    private boolean isLast;
}
