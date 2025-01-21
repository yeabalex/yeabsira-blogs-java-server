package com.blog.blog_server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;

@Document("replies")
@AllArgsConstructor
@NoArgsConstructor
public class CommentReplyModel {
    @Id
    @Getter
    private String id;

    @Setter
    @Getter
    private String reply;

    @Getter
    @Setter
    private String commentID;

    @Getter
    @Setter
    private String articleID;

    @Getter
    @Setter
    private String userID;

    @Getter
    @Setter
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


