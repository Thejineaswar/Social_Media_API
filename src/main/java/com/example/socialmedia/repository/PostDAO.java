package com.example.socialmedia.repository;

import com.example.socialmedia.model.Post;
import com.example.socialmedia.model.SmUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.awt.*;
import java.util.List;

public interface PostDAO extends CrudRepository<Post,Long>{
    List<Post> findByPostOwner(SmUser user);

    Post findPostById(Long Id);

    @Query(value = "select * from post p inner join (select * from follow f where f.follower = ?1) as f " +
            " on f.username = p.username",
        nativeQuery = true
    )
    List<Post> extractSomePosts(String username);
    @Transactional
    void deleteById(Long Id);
//
//    void
}
