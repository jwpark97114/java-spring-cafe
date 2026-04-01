package com.codesquad.reply;

import com.codesquad.article.Article;
import com.codesquad.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@SQLRestriction("deleted = false")
@SQLDelete(sql = "UPDATE replies SET deleted = true WHERE id = ?")
@Table(name="replies")
public class Reply {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_pk")
    private User user;

    @ManyToOne
    @JoinColumn(name ="article_pk")
    private Article article;

    private String reply;

    @CreatedDate
    private LocalDateTime createdAt;

    private boolean deleted = false;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
