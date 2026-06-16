package com.techpick.backend.bookmark.dto;

public record BookmarkItemResponse(
   Long bookmarkId,
   Long articleId,
   String title,
   String url,
   String thumbnailUrl,
   String blogName,
   String createdAt
) {}
