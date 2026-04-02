package com.codesquad.cafeRepo;

import com.codesquad.article.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaArticleRepo extends JpaRepository<Article, Integer> {

    public Article findArticleById(int id);

    public Page<Article> findAll(Pageable pageable);
}
