package com.pokerogue.helper.article.repository;

import com.pokerogue.helper.article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
