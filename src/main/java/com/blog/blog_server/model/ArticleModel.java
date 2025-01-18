package com.blog.blog_server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;
import java.util.List;

@Document(collection = "articles")
@AllArgsConstructor
@NoArgsConstructor
public class ArticleModel {
    @Getter
    @Id
    private String id;

    @Setter
    @Getter
    private String title;
    @Setter
    @Getter
    private String description;
    @Setter
    @Getter
    private String thumbnail;
    @Setter
    @Getter
    private String content;
    @Setter
    @Getter
    private List<String> topics;
    @Setter
    @Getter
    private int likes;
    @Setter
    @Getter
    private int views;
    @Setter
    @Getter
    private int comments;
    @Setter
    @Getter
    private String username;
    @Setter
    @Getter
    private String userid;
    @Transient
    @Setter
    @Getter
    private boolean isLiked;

    @CreatedDate
    @Getter
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Getter
    @Setter
    private OffsetDateTime updatedAt;
}
