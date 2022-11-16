package com.example.socialmedia.controller;

import com.example.socialmedia.model.Post;
import com.example.socialmedia.repository.PostDAO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@AllArgsConstructor
public class FeedController {
    private PostDAO postDAO;
    @GetMapping(value = "/getFeed")
    public ResponseEntity getFeed(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(username);
        try{
            List<Post> posts = postDAO.extractSomePosts(username);
            return ResponseEntity.ok().body(posts);
        }catch(Exception e){
            return ResponseEntity.internalServerError().body("Unable to extract user feed");
        }
    }
}
