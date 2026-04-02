package com.codesquad.service;

import com.codesquad.article.Article;
import com.codesquad.cafeRepo.ArticleRepo;
import com.codesquad.cafeRepo.JpaArticleRepo;
import com.codesquad.exceptions.ForbiddenAccessException;
import com.codesquad.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ArticleService {

    private JpaArticleRepo repo;

    @Autowired
    public ArticleService(JpaArticleRepo repo){
        this.repo = repo;
    }

    public List<Article> getAllArticles(){
        return repo.findAll();
    }

    public Page<Article> getArticlesPage(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return this.repo.findAll(pageable);
    }

    public void putNewArticle(User currentUser, Article newArticle){
        newArticle.setUser(currentUser);
        newArticle.setAuthor();
        repo.save(newArticle);
    }

    public Article findArticleById(int id){
        return this.repo.findArticleById(id);
    }

    public void deleteArticle(Article targetArticle, User sessionUser){
        if(targetArticle.getUser().equals(sessionUser)){
            repo.delete(targetArticle);
        }
    }

    public Article findArticleForEdit(int id, User currentUser){
        Article foundArticle = this.repo.findArticleById(id);

        if(foundArticle == null || (!foundArticle.getUser().equals(currentUser))){
            throw new ForbiddenAccessException("YOU ARE ONLY ALLOWED TO MODIFY YOUR OWN POSTS");
        }
        return foundArticle;
    }

}
