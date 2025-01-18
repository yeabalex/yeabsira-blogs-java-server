package com.blog.blog_server.controller;

import com.blog.blog_server.model.CommentModel;
import com.blog.blog_server.service.CommentService;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/api/v1/add/comment")
    @ResponseBody
    public ResponseEntity<?> addComment(@RequestBody CommentModel comment) {
        try {
            commentService.addComment(comment);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/api/v1/get/comments/{articleID}")
    @ResponseBody
    public ResponseEntity<Page<CommentModel>> getComments(
            @PathVariable String articleID,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
                System.out.println(articleID);
        return ResponseEntity.ok().body(commentService.getComments(articleID, page, size));
    }

}
