package com.pokerogue.helper.article.controller;

import com.pokerogue.helper.article.dto.ArticleResponse;
import com.pokerogue.helper.article.service.ArticleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/api/v1/articles")
    public List<ArticleResponse> articleList() {
        return articleService.findArticles();
    }
}
