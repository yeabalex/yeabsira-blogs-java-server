package com.blog.blog_server.model;

import lombok.Getter;
import lombok.Setter;

public class AIRequestBody {
    @Getter
    @Setter
    private PromptClass body;


    public static class PromptClass{
        @Getter
        @Setter
        private String prompt;
    }
}
