package com.blog.blog_server.repository;

import com.blog.blog_server.model.CommentReplyModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ReplyRepo extends MongoRepository<CommentReplyModel, String> {
    Optional<CommentReplyModel> findById(String replyId);
}

