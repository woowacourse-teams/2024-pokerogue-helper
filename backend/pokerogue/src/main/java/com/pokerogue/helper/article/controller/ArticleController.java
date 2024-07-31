package com.pokerogue.helper.article.controller;

import com.pokerogue.helper.article.dto.ArticleDetailsResponse;
import com.pokerogue.helper.article.dto.ArticleResponse;
import com.pokerogue.helper.article.service.ArticleService;
import com.pokerogue.helper.util.dto.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/api/v1/articles")
    public ApiResponse<List<ArticleResponse>> articleList() {
        return new ApiResponse<>("꿀팁 리스트 불러오기에 성공했습니다.", articleService.findArticles());
    }

    @GetMapping("/api/v1/article/{id}")
    public ApiResponse<ArticleDetailsResponse> articleDetails(@PathVariable("id") Long id) {
        log.info("---- URI : {}, Param : {}", "/api/v1/article/{id}", id);

        return new ApiResponse<>("꿀팁 상세 정보 불러오기에 성공했습니다.", articleService.findArticleDetails(id));
    }
}
