package com.blog.blog_server.repository;

import com.blog.blog_server.model.CommentReplyModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ReplyRepo extends MongoRepository<CommentReplyModel, String> {
    Optional<CommentReplyModel> findById(String replyId);
    @Query("{}")
    Page<CommentReplyModel> findByCommentIDOrderByCreatedAtDesc(String commentID, Pageable page);
}

