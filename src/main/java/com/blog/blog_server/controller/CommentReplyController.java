package com.blog.blog_server.controller;

import com.blog.blog_server.model.CommentReplyModel;
import com.blog.blog_server.service.CommentsReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CommentReplyController {
    private final CommentsReplyService commentsReplyService;
    public CommentReplyController(CommentsReplyService commentsReplyService) {
        this.commentsReplyService = commentsReplyService;
    }

    @PostMapping("/api/v1/reply")
    @ResponseBody
    public ResponseEntity<?> addReply(@RequestBody CommentReplyModel reply) {
        try {
            commentsReplyService.postReply(reply);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/api/v1/reply/{commentID}")
    @ResponseBody
    public ResponseEntity<?> getReplies(@PathVariable String commentID,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok().body(commentsReplyService.getReplies(commentID, page, size));
    }
    
}
