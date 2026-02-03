package com.techpick.backend.article.dto.response;

import com.techpick.backend.article.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponse {
    private Long articleId;
    private String title;
    private String url;
    private String content;
    private String pubDate;
    private String blogName;
    private String thumbnailUrl;

    public static ArticleResponse from(Article article) {
        return ArticleResponse.builder()
                .articleId(article.getArticleId())
                .title(article.getTitle())
                .url(article.getUrl())
                .content(article.getContent())
                .pubDate(article.getPubDate())
                .blogName(article.getBlogName())
                .thumbnailUrl(article.getThumbnailUrl())
                .build();
    }
}
