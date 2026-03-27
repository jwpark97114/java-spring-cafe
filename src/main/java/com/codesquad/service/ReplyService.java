package com.codesquad.service;

import com.codesquad.article.Article;
import com.codesquad.cafeRepo.JpaReplyRepo;
import com.codesquad.reply.Reply;
import com.codesquad.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReplyService {

    private final JpaReplyRepo repo;

    @Autowired
    public ReplyService(JpaReplyRepo repo){
        this.repo = repo;
    }

    public List<Reply> findRepliesForArticle(int articleId){
        return this.repo.findReplyByArticleId(articleId);
    }

    public Reply findReplyById(long id){
        return this.repo.findById(id).get();
    }

    public void removeReply(long id){
        this.repo.deleteById(id);
    }

    public void addReply(Article article, User user, String content){
        Reply newReply = new Reply();
        newReply.setArticle(article);
        newReply.setUser(user);
        newReply.setCreatedAt(LocalDateTime.now());
        newReply.setReply(content);
        this.repo.save(newReply);
    }

}
