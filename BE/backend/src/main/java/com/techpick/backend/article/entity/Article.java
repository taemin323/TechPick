package com.techpick.backend.article.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Getter
@Entity
@Table(name = "articles")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String url;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String pubDate;

    @Column(nullable = false)
    private String blogName;

    @Column(nullable = false)
    private String thumbnailUrl;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
