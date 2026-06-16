package com.techpick.backend.bookmark.controller;

import com.techpick.backend.bookmark.dto.BookmarkListResponse;
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

    /**
     * 북마크 추가/취소
     * @param articleId
     * @param userUuid
     * @return
     */
    @PostMapping("/{articleId}")
    public ApiResponse<BookmarkToggleResponse> toggleBookmark(
            @PathVariable Long articleId,
            @RequestHeader("X-USER-ID") String userUuid
    ) {
        BookmarkToggleResponse response = bookmarkService.toggleBookmark(userUuid, articleId);

        if("ADDED".equals(response.action())) return ApiResponse.of(SuccessStatus.BOOKMARK_ADDED_SUCCESS, response);

        return ApiResponse.of(SuccessStatus.BOOKMARK_DELETED_SUCCESS, response);
    }

    /**
     * 북마크 목록 조회
     * @param page
     * @param size
     * @param userUuid
     * @return
     */
    @GetMapping()
    public ApiResponse<BookmarkListResponse> getBookmarks(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestHeader("X-USER-ID") String userUuid
    ) {
        BookmarkListResponse response = bookmarkService.getBookmarkList(page, size, userUuid);

        return ApiResponse.of(SuccessStatus.BOOKMARK_LIST_SUCCESS, response);
    }
}
