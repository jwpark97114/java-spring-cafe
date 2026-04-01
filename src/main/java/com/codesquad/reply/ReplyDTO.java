package com.codesquad.reply;

import java.time.LocalDateTime;

public class ReplyDTO {

    private long id;
    private String user_id;
    private int articleId;
    private LocalDateTime createdAt;
    private String reply;

    public ReplyDTO(long id, String user_id,int articleId, LocalDateTime createdAt, String reply){
        this.id = id;
        this.articleId = articleId;
        this.user_id = user_id;
        this.createdAt = createdAt;
        this.reply = reply;
    }

    public static ReplyDTO of(Reply reply){
        return new ReplyDTO(reply.getId(),reply.getUser().getId(),reply.getArticle().getId(),reply.getCreatedAt(),reply.getReply());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }
}
