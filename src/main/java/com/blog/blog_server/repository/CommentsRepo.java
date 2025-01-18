package com.blog.blog_server.repository;

import com.blog.blog_server.model.CommentModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CommentsRepo extends MongoRepository<CommentModel,String> {
    Optional<CommentModel> findById(String id);
    @Query("{}")
    Page<CommentModel> findByArticleIDOrderByCreatedAtDesc(String articleID, Pageable pageable);
}
