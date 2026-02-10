package com.techpick.backend.bookmark.service;

import com.techpick.backend.article.entity.Article;
import com.techpick.backend.article.repository.ArticleRepository;
import com.techpick.backend.bookmark.dto.BookmarkToggleResponse;
import com.techpick.backend.bookmark.entity.Bookmark;
import com.techpick.backend.bookmark.repository.BookmarkRepository;
import com.techpick.backend.common.apiPayload.code.status.ErrorStatus;
import com.techpick.backend.common.apiPayload.exception.GeneralException;
import com.techpick.backend.user.entity.User;
import com.techpick.backend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    public BookmarkToggleResponse toggleBookmark(String uuid, Long articleId) {
        // 유저 조회
        User user = userRepository.findByUserUuid(uuid)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        // 아티클 조회
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ARTICLE_NOT_FOUND));

        // 북마크 존재 여부 확인
        Optional<Bookmark> bookmark = bookmarkRepository.findByUserAndArticle(user, article);

        if(bookmark.isPresent()) {
            bookmarkRepository.delete(bookmark.get());
            return new BookmarkToggleResponse("DELETED", java.time.LocalDateTime.now(ZoneOffset.UTC));
        } else {
            Bookmark newBookmark = Bookmark.builder()
                    .user(user)
                    .article(article)
                    .createdAt(LocalDateTime.now(ZoneOffset.UTC))
                    .build();
            bookmarkRepository.save(newBookmark);
            return new BookmarkToggleResponse("ADDED", java.time.LocalDateTime.now(ZoneOffset.UTC));
        }
    }
}
