package com.codesquad.cafeRepo;

import com.codesquad.reply.Reply;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaReplyRepo extends JpaRepository<Reply, Long> {

    public Slice<Reply> findReplyByArticleId(int articleId, Pageable pageable);

}
