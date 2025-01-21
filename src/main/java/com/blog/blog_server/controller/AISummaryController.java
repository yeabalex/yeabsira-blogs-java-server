package com.blog.blog_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.blog_server.model.AIRequestBody;
import com.blog.blog_server.model.AISummaryResponse;
import com.blog.blog_server.service.AISummaryService;

@Controller
public class AISummaryController {
    @Autowired
    private AISummaryService aiSummaryService; 

    @PostMapping("/api/v1/ai")
    @ResponseBody
    public ResponseEntity<AISummaryResponse> getAISummary(@RequestBody AIRequestBody requestBody){
        try{
        AISummaryResponse aiSummaryResponse = aiSummaryService.getAISummary(requestBody);
        return ResponseEntity.ok().body(aiSummaryResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    } 
}
