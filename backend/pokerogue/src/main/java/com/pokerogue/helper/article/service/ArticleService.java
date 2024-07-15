package com.pokerogue.helper.article.service;

import com.pokerogue.helper.article.domain.Article;
import com.pokerogue.helper.article.dto.ArticleResponse;
import com.pokerogue.helper.article.repository.ArticleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<ArticleResponse> findArticles() {
        List<Article> articles = articleRepository.findAll();

        return articles.stream()
                .map(ArticleResponse::from)
                .toList();
    }
}
