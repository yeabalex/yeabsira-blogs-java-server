package com.blog.blog_server.repository;

import com.blog.blog_server.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<UserModel, String> {
    Optional<UserModel> findById(String id);
}
