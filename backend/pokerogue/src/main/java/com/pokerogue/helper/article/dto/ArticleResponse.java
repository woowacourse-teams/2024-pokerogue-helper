package com.pokerogue.helper.article.dto;

import com.pokerogue.helper.article.domain.Article;

public record ArticleResponse(Long id, String title, String thumbnail) {

    public static ArticleResponse from(Article article) {
        return new ArticleResponse(article.getId(), article.getTitle(), article.getThumbnail());
    }
}
