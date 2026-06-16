package com.techpick.backend.bookmark.dto;

import com.techpick.backend.article.dto.response.ArticleResponse;
import lombok.Builder;

import java.util.List;
@Builder
public record BookmarkListResponse(
    List<BookmarkItemResponse> bookmarks,
    int listSize,
    int totalPages,
    long totalElements,
    boolean isFirst,
    boolean isLast
){}
