package com.blog.blog_server.service;

import com.blog.blog_server.model.ArticleModel;
import com.blog.blog_server.model.CommentModel;
import com.blog.blog_server.model.CommentReplyModel;
import com.blog.blog_server.repository.ArticleRepo;
import com.blog.blog_server.repository.CommentsRepo;
import com.blog.blog_server.repository.ReplyRepo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CommentsReplyService {
    private final ReplyRepo replyRepo;
    private final CommentsRepo commentsRepo;
    private final ArticleRepo articleRepo;

    public CommentsReplyService(ReplyRepo replyRepo, CommentsRepo commentsRepo, ArticleRepo articleRepo) {
        this.replyRepo = replyRepo;
        this.commentsRepo = commentsRepo;
        this.articleRepo = articleRepo;
    }

    public void postReply(CommentReplyModel commentReplyModel) {
        try {
            Optional<CommentModel> comment = commentsRepo.findById(commentReplyModel.getCommentID());
            Optional<ArticleModel> article = articleRepo.findById(commentReplyModel.getArticleID());

            if ((comment.isPresent() && article.isPresent())) {
                replyRepo.save(commentReplyModel);
                comment.ifPresent(nullComment -> {
                    nullComment.setReplies(nullComment.getReplies() + 1);
                    commentsRepo.save(nullComment);
                });
            }
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    public Page<CommentReplyModel> getReplies(String commentID, int page, int size) {
        return replyRepo.findByCommentIDOrderByCreatedAtDesc(commentID, PageRequest.of(page, size));
    }
}
