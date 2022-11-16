package com.example.socialmedia.controller;

import com.example.socialmedia.model.Post;
import com.example.socialmedia.model.SmUser;
import com.example.socialmedia.payload.PostRequest;
import com.example.socialmedia.repository.PostDAO;
import com.example.socialmedia.repository.SmUserDAO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.awt.dnd.DropTarget;
import java.util.Date;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
public class PostController{
    private SmUserDAO smUserDAO;
    private PostDAO postDAO;

    @PostMapping(value = "/createPost")
    public ResponseEntity createPost(@RequestBody PostRequest postRequest){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Post newPost = new Post();
        SmUser user = smUserDAO.findByUsername(username);
        newPost.setPostOwner(user);
        newPost.setDescription(postRequest.getDescription());
        newPost.setImageLink(postRequest.getImageLink());
        newPost.setDatePosted(new Date());
        newPost.setDateUpdated(new Date());

        try{
            postDAO.save(newPost);
        }catch(Exception e){
            ResponseEntity.internalServerError().body("Please Try posting after some time");
        }
        return ResponseEntity.ok("The post has been posted");
    }

    @GetMapping(value = "/getPosts")
    public ResponseEntity getPosts(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        SmUser user = smUserDAO.findByUsername(username);
        List<Post> postArr = postDAO.findByPostOwner(user);
        return ResponseEntity.ok().body(postArr);
    }

    @PatchMapping(value = "/updatePost")
    public ResponseEntity updatePost(@RequestBody PostRequest postRequest){
        Long postId;
        try{
            postId = postRequest.getId();
        }catch(Exception e){
            return ResponseEntity.status(400).body("Post ID field is null");
        }
        Post toBeUpdated;
        try{
            toBeUpdated = postDAO.findPostById(postId);
            if(postRequest.getDescription() != null){
                toBeUpdated.setImageLink(postRequest.getImageLink());
            }
            if(postRequest.getImageLink() != null){
                toBeUpdated.setImageLink(postRequest.getImageLink());
            }
            if(postRequest.getDescription() != null){
                toBeUpdated.setDescription(postRequest.getDescription());
            }
            toBeUpdated.setDateUpdated(new Date());

        }catch (Exception e){
            return ResponseEntity.status(400).body("No Post with the given post id exists");
        }
        try{
            postDAO.save(toBeUpdated);
        }catch(RuntimeException e){
            return ResponseEntity.internalServerError().body("Try updating after some time");
        }
        return ResponseEntity.ok().body("Post Updated");
    }

    @DeleteMapping(value = "/deletePost")
    public ResponseEntity deletePost(@RequestBody Map<String, Object> payload){
        if(payload.containsKey("postId")){
            Long id = ((Number) payload.get("postId")).longValue();
            try{
                postDAO.deleteById(id);
            }catch (Exception e){
                return ResponseEntity.status(400).body("No Post with the given Post Id exists");
            }
            return ResponseEntity.ok().body("Post deleted successfully");
        }else{
            return ResponseEntity.status(400).body("Post Id not present in the body");
        }
    }
}
