package com.blog.blog_server.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;

@Document("comments")
public class CommentModel {
    @Id
    @Getter
    private String id;
    @Setter
    @Getter
    private String content;
    @Setter
    @Getter
    private Integer replies;
    @Setter
    @Getter
    private String userID;
    @Setter
    @Getter
    private String articleID;
    @Setter
    @Getter
    private String username;

    @CreatedDate
    @Getter
    @Setter
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Getter
    @Setter
    private OffsetDateTime updatedAt;
}
