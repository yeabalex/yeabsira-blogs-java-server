package com.blog.blog_server.service;

import com.blog.blog_server.model.ArticleModel;
import com.blog.blog_server.model.CommentModel;
import com.blog.blog_server.repository.ArticleRepo;
import com.blog.blog_server.repository.CommentsRepo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {
    private final CommentsRepo commentsRepo;
    private final ArticleRepo articleRepo;
    public CommentService(CommentsRepo commentsRepo, ArticleRepo articleRepo) {
        this.commentsRepo = commentsRepo;
        this.articleRepo = articleRepo;
    }

    public void addComment(CommentModel comment) {
        try{
            Optional<ArticleModel> article = articleRepo.findById(comment.getArticleID());
            article.ifPresent(articleModel -> {
                articleModel.setComments(articleModel.getComments() + 1);
                articleRepo.save(articleModel);
                commentsRepo.save(comment);
            });
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Page<CommentModel> getComments(String articleId, int page, int size) {
        return commentsRepo.findByArticleIDOrderByCreatedAtDesc(articleId, PageRequest.of(page, size));
    }
}
