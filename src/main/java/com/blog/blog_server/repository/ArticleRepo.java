package com.blog.blog_server.repository;

import com.blog.blog_server.model.ArticleModel;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepo extends MongoRepository<ArticleModel, String> {
    Optional<ArticleModel> findById(String id);
    @Query(value = "{}", fields = "{id: 1, thumbnail: 1, title: 1, description: 1, likes: 1, views: 1, comments: 1, username: 1, createdAt: 1, topics: 1}")
    List<ArticleModel> findAllByOrderByCreatedAtDesc(Pageable pageable);
}