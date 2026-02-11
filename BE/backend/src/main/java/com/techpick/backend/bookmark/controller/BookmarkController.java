package com.techpick.backend.bookmark.controller;

import com.techpick.backend.bookmark.dto.BookmarkToggleResponse;
import com.techpick.backend.bookmark.service.BookmarkService;
import com.techpick.backend.common.apiPayload.ApiResponse;
import com.techpick.backend.common.apiPayload.code.status.SuccessStatus;
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

        if("ADDED".equals(response.action())) return ApiResponse.of(SuccessStatus.BOOKMARK_ADDED_SUCCESS, response);

        return ApiResponse.of(SuccessStatus.BOOKMARK_DELETED_SUCCESS, response);
    }
}
