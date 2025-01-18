package com.blog.blog_server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.blog.blog_server.model.LikesModel;
import java.util.Optional;


@Repository
public interface LikeRepo extends MongoRepository<LikesModel, String> {
    Optional<LikesModel> findByUserID(String userID);
}
