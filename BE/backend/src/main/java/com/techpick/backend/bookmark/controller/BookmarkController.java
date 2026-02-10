package com.techpick.backend.bookmark.controller;

import com.techpick.backend.bookmark.dto.BookmarkToggleResponse;
import com.techpick.backend.bookmark.service.BookmarkService;
import com.techpick.backend.common.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/{articleId}")
    public ApiResponse<BookmarkToggleResponse> toggleBookmark(
            @PathVariable Long articleId,
            @RequestHeader("X-USER-ID") String userUuid
    ) {
        BookmarkToggleResponse response = bookmarkService.toggleBookmark(userUuid, articleId);
        return ApiResponse.onSuccess(response);
    }
}
