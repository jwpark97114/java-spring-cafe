package com.codesquad.service;

import com.codesquad.article.Article;
import com.codesquad.cafeRepo.JpaReplyRepo;
import com.codesquad.exceptions.ForbiddenAccessException;
import com.codesquad.reply.Reply;
import com.codesquad.reply.ReplyDTO;
import com.codesquad.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReplyService {

    private final JpaReplyRepo repo;
    private final ArticleService articleService;

    @Autowired
    public ReplyService(JpaReplyRepo repo, ArticleService service){
        this.repo = repo;
        this.articleService = service;
    }

    public List<Reply> findRepliesForArticle(int articleId){
        return this.repo.findReplyByArticleId(articleId);
    }

    public List<ReplyDTO> findRepliesDTOForArticle(int articleId){
        List<Reply> returnedReplies = this.findRepliesForArticle(articleId);
        return returnedReplies.stream().map( r -> new ReplyDTO(r.getId(), r.getUser().getId(), r.getArticle().getId() ,r.getCreatedAt(),r.getReply())).toList();
    }

    public Reply findReplyById(long id){
        return this.repo.findById(id).get();
    }

    public Reply findReplyToEditById(long id, User user){
        Reply targetReply = this.repo.findById(id).orElse(null);

        if(targetReply == null || !(targetReply.getUser().equals(user))){
            throw new ForbiddenAccessException("YOU CANNOT EDIT OTHERS REPLY");
        }

        return targetReply;
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

    public ReplyDTO addReplyToArticle(int articleId, User user, String replyComment){
        Reply newReply = new Reply();
        Article targetArticle = this.articleService.findArticleById(articleId);
        newReply.setArticle(targetArticle);
        newReply.setUser(user);
        newReply.setCreatedAt(LocalDateTime.now());
        newReply.setReply(replyComment);
        this.repo.save(newReply);
        return ReplyDTO.of(newReply);
    }

    public boolean removeReplyWithIds( long replyId, User user){
        Reply targetReply = this.repo.findById(replyId).orElse(null);
        if(targetReply == null || !(targetReply.getUser().equals(user))){
            throw new ForbiddenAccessException("YOU CANNOT DELETE OTHER'S REPLY");
        }
        this.repo.deleteById(replyId);
        return true;
    }
}

