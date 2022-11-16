package com.example.socialmedia.repository;

import com.example.socialmedia.model.Follow;
import com.example.socialmedia.model.SmUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FollowDAO extends CrudRepository<Follow,Long>{

    List<SmUser> findByFollowedUser(String username);

    Integer countByFollowedUser(SmUser user);
    Integer countByFollower(String username);

    Follow findByFollowedUserAndFollower(SmUser user, String Follower);

    List<SmUser> findByFollower(String username);
}
