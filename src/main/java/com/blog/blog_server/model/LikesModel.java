package com.blog.blog_server.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document("likes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LikesModel {
    @Getter
    @Setter
    @Id
    private String userID;

    private Set<String> likedPosts = new HashSet<>();

    public void addLikedPost(String postID) {
        if (postID != null && !postID.isEmpty()) {
            likedPosts.add(postID);
        } else {
            throw new IllegalArgumentException("Post ID cannot be null or empty");
        }
    }

    public void removeLikedPost(String postID) {
        if (postID != null && !postID.isEmpty()) {
            likedPosts.remove(postID);
        } else {
            throw new IllegalArgumentException("Post ID cannot be null or empty");
        }
    }

    public Set<String> getLikedPosts() {
        return Collections.unmodifiableSet(likedPosts);
    }

    public boolean checkIfUserLikedPost(String postID) {
        return likedPosts.contains(postID);
    }
}
