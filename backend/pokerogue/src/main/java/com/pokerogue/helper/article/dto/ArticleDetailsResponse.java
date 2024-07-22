package com.pokerogue.helper.article.dto;

import com.pokerogue.helper.article.domain.Article;

public record ArticleDetailsResponse(Long id, String thumbnail, String title, String content) {

    public static ArticleDetailsResponse from(final Article article) {
        return new ArticleDetailsResponse(article.getId(), article.getThumbnail(), article.getTitle(),
                article.getThumbnail());
    }
}
