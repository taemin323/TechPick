package com.techpick.backend.bookmark.repository;

import com.techpick.backend.article.entity.Article;
import com.techpick.backend.bookmark.entity.Bookmark;
import com.techpick.backend.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByUserAndArticle(User user, Article article);
    Page<Bookmark> findAllByUser(User user, Pageable pageable);
}
