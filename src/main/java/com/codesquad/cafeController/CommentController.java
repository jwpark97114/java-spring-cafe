package com.codesquad.cafeController;

import com.codesquad.reply.ReplyDTO;
import com.codesquad.service.ReplyService;
import com.codesquad.user.LoginRequired;
import com.codesquad.user.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final ReplyService replyService;


    public CommentController( ReplyService rs){
        this.replyService = rs;
    }

    @LoginRequired
    @GetMapping("/{articleId}")
    public ResponseEntity<Slice<ReplyDTO>> getCommentsForArticle(@PathVariable int articleId, @RequestParam(defaultValue = "0") int page){

        return ResponseEntity.status(200).body(this.replyService.findReplySliceByArticleId(articleId,page));
    }

    @LoginRequired
    @PostMapping("/{articleId}")
    public ResponseEntity<ReplyDTO> postCommentToArticle(@PathVariable int articleId, @SessionAttribute(value = "currentUser") User user, @RequestParam String commentContent){
        ReplyDTO returnedDTO = this.replyService.addReplyToArticle(articleId, user, commentContent);
        return ResponseEntity.status(201).body(returnedDTO);
    }

    @LoginRequired
    @DeleteMapping("/{articleId}/{commentId}")
    public ResponseEntity<String> deleteCommentFromArticle(@PathVariable int articleId, @PathVariable long commentId, @SessionAttribute(value = "currentUser") User user){
        boolean successfulDeletion = this.replyService.removeReplyWithIds(commentId, user);
        if(successfulDeletion){
            return ResponseEntity.ok("Deleted");
        }

        return ResponseEntity.status(403).body("FAILED");
    }




}
