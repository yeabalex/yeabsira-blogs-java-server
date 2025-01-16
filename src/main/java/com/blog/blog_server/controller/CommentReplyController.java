package com.blog.blog_server.controller;

import com.blog.blog_server.model.CommentReplyModel;
import com.blog.blog_server.service.CommentsReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommentReplyController {
    private final CommentsReplyService commentsReplyService;
    public CommentReplyController(CommentsReplyService commentsReplyService) {
        this.commentsReplyService = commentsReplyService;
    }

    @PostMapping("/api/v1/add/reply")
    @ResponseBody
    public ResponseEntity<?> postReply(@RequestBody CommentReplyModel commentReplyModel) {
        try {
            commentsReplyService.postReply(commentReplyModel);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
