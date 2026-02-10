package com.techpick.backend.bookmark.service;

import com.techpick.backend.article.entity.Article;
import com.techpick.backend.article.repository.ArticleRepository;
import com.techpick.backend.bookmark.dto.BookmarkToggleResponse;
import com.techpick.backend.bookmark.entity.Bookmark;
import com.techpick.backend.bookmark.repository.BookmarkRepository;
import com.techpick.backend.user.entity.User;
import com.techpick.backend.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookmarkServiceTest {

    @InjectMocks
    private BookmarkService bookmarkService;

    @Mock
    private BookmarkRepository bookmarkRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("북마크가 존재하지 않을 때 토글을 호출하면 새 북마크가 저장된다.")
    void toggleBookmarkCreate() {
        //Given
        String uuid = "test-uuid";
        Long articleId = 1L;
        User user = User.builder().userId(1L).userUuid(uuid).build();
        Article article = Article.builder().articleId(articleId).build();

        given(userRepository.findByUserUuid(uuid)).willReturn(Optional.of(user));
        given(articleRepository.findById(articleId)).willReturn(Optional.of(article));
        given(bookmarkRepository.findByUserAndArticle(user, article)).willReturn(Optional.empty());

        //When
        BookmarkToggleResponse response = bookmarkService.toggleBookmark(uuid, articleId);

        //Then
        verify(bookmarkRepository, times(1)).save(any(Bookmark.class));
        assertThat(response.action()).isEqualTo("ADDED");
    }
}
