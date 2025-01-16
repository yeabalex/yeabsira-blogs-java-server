package com.blog.blog_server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;

@Document("replies")
@NoArgsConstructor
@AllArgsConstructor
public class CommentReplyModel {
    @Id
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
    private OffsetDateTime createdAt;

    @Getter
    @Setter
    private OffsetDateTime updatedAt;
}


