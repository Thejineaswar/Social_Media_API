package com.example.socialmedia.controller;


import com.example.socialmedia.model.Follow;
import com.example.socialmedia.model.SmUser;
import com.example.socialmedia.payload.FollowRequest;
import com.example.socialmedia.repository.FollowDAO;
import com.example.socialmedia.repository.SmUserDAO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
public class FollowController {

    private FollowDAO followDAO;
    private SmUserDAO smUserDAO;

    @PostMapping(value = "/follow")
    public ResponseEntity follow(@RequestBody FollowRequest followRequest){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth.getName().equals(followRequest.getTo_follow())){
            return ResponseEntity.status(400).body("User cannot follow themselves");
        }

        SmUser toFollow = smUserDAO.findByUsername(followRequest.getTo_follow());
        if(toFollow != null){
            try{
                Follow newFollow = new Follow();
                newFollow.setFollower(auth.getName());
                newFollow.setFollowed_user(toFollow);
                followDAO.save(newFollow);
            }catch(RuntimeException e){
                return ResponseEntity.status(400).body("Already the user has been followed by current user");
            }
        }else{
            return ResponseEntity.status(400).body("The current user is trying to follow a user who does not exist");
        }
        return ResponseEntity.ok("Current user now follows " + followRequest.getTo_follow());
    }

    @DeleteMapping(value="/unfollow")
    public ResponseEntity unfollow(@RequestBody Map<String, Object> payload){
        String curr_user = SecurityContextHolder.getContext().getAuthentication().getName(); // follower
        if(payload.containsKey("username")){
            try{
                String to_unfollow = (String) payload.get("username");
                SmUser unfollow_user = smUserDAO.findByUsername(to_unfollow);
                Follow followEntity = followDAO.findByFollowedUserAndFollower(unfollow_user,curr_user);
                followDAO.deleteById(followEntity.getId());
                return ResponseEntity.ok().body("Unfollow operation successful");
            }catch (Exception e){
                return ResponseEntity.internalServerError().body("Unfollow operation not successful");
            }
        }else{
            return ResponseEntity.status(400).body("Include 'username' in json body");
        }
    }

    @PostMapping(value = "/followerDetails")
    public ResponseEntity followerDetails(@RequestBody FollowRequest followRequest){
        SmUser user =  smUserDAO.findByUsername(followRequest.getTo_follow());
        if(user != null){
            int result =  followDAO.countByFollower(followRequest.getTo_follow());
            HashMap<String,Object> mp = new HashMap<>();
            mp.put("numFollowing", result);
            result = followDAO.countByFollowedUser(user);
            mp.put("numFollowers", result);

            return ResponseEntity.ok(mp);
        }
        return ResponseEntity.status(400).body("Invalid Username");
    }

}
