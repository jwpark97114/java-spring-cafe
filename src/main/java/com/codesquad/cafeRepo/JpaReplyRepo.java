package com.codesquad.cafeRepo;

import com.codesquad.reply.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaReplyRepo extends JpaRepository<Reply, Long> {

    public List<Reply> findReplyByArticleId(int articleId);
}
