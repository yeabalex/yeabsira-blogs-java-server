package com.blog.blog_server.controller;

import com.blog.blog_server.model.ArticleModel;
import com.blog.blog_server.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ArticleController {
    private final ArticleService articleService;
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/api/v1/add/article")
    @ResponseBody
    public ResponseEntity<?> addArticle(@RequestBody ArticleModel articleModel) {
        articleService.postArticle(articleModel);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v1/articles")
    @ResponseBody
    public ResponseEntity<ArticleModel> getArticleById(@RequestParam("id") String id, @RequestParam("userid") String userID) {
        try {
            ArticleModel article = articleService.getArticle(id, userID);
            return ResponseEntity.ok().body(article);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/v1/like")
    @ResponseBody
    public ResponseEntity<?> likeArticle(@RequestParam("id") String id, @RequestParam("action") String action, @RequestParam("userid") String userid) {
        try {
            articleService.likeArticle(id, action, userid);
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/v1/trending")
    @ResponseBody
    public ResponseEntity<List<ArticleModel>> getTrendingArticle(@RequestParam("userid") String userid) {
        if(userid.isEmpty()){
            return ResponseEntity.ok().body(articleService.getLatest7Articles(""));
        }
        return ResponseEntity.ok().body(articleService.getLatest7Articles(userid));
    }
}
